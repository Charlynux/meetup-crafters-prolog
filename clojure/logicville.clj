(ns meetup_crafters.prolog.logicville
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic
             :refer [run run* == != membero lvar defne
                     everyg fresh matche distincto all succeed
                     conde]]
            [clojure.core.logic.fd :as fd]
            [cognitect.transcriptor :as xr :refer (check!)]))

(defn conditions-domaine [domaine xs n]
  (let [schema (repeatedly n lvar)
        domaine-restreint (take n domaine)]
    (all
     (== xs schema)
     (everyg #(membero % domaine-restreint) xs)
     (distincto xs))))

(def COULEURS [:jaune :rouge :vert :blanc :bleu])

(defn conditions-maisons [maisons nombre]
  (conditions-domaine COULEURS maisons nombre))

(def ANIMAUX [:oiseau :tortue :lapin :chien :chat])

(defn conditions-niches [niches nombre]
  (conditions-domaine ANIMAUX niches nombre))

(defne voisins [houses a b]
  ([[a b . tail] _ _])
  ([[b a . tail] _ _])
  ([[_ . tail] _ _] (voisins tail a b)))

(defmacro place [houses hints]
  `(matche [~houses]
           ([~hints] succeed)))

(defmacro solution_carte
  [card-name & rules]
  `(run* [~'l]
     (conditions-maisons ~'l 3)
     ~@rules))

(solution_carte
 "Carte 1"
 (place l [_ _ :vert])
 (place l [:jaune _ _]))
(check! #{(list [:jaune :rouge :vert])})

(solution_carte
 "Carte 2"
 (place l [:vert _ _])
 (matche [l]
         ([[_ X _]]
          (!= X :rouge))))
(check! #{(list [:vert :jaune :rouge])})

(solution_carte
 "Carte 3"
 (matche [l]
         ([[_ M D]]
          (!= M :vert)
          (!= M :jaune)
          (!= D :vert))))
(check! #{(list [:vert :rouge :jaune])})

(solution_carte
 "Carte 4"
 (matche [l]
         ([[G M _]]
          (!= G :vert)
          (!= M :rouge)))
 (voisins l :rouge :jaune))
(check! #{(list [:rouge :jaune :vert])})

(solution_carte
 "Carte 5"
 (place l [:rouge _ _])
 (voisins l :vert :rouge))
(check! #{(list [:rouge :vert :jaune])})

(defne pas-voisins [l a b]
  ([[a _ b] _ _])
  ([[b _ a] _ _]))

(solution_carte
 "Carte 7"
 (place l [_ _ :rouge])
 (pas-voisins l :rouge :vert))
(check! #{(list [:vert :jaune :rouge])})

(solution_carte
 "Carte 8"
 (pas-voisins l :rouge :vert)
 (matche [l]
         ([[G _ _]]
          (!= G :vert))))
(check! #{(list [:rouge :jaune :vert])})

(defne est-a-gauche [l a b]
  ([[a . tail] _ _]
   (membero b tail))
  ([[x . tail] _ _]
   (!= x b)
   (est-a-gauche tail a b)))

(solution_carte
 "Carte 9"
 (voisins l :rouge :jaune)
 (voisins l :jaune :vert)
 (est-a-gauche l :vert :rouge))
(check! #{(list [:vert :jaune :rouge])})

;; Carte 25 - Première carte avec des animaux.

(defne voisins-humain-animal
  [maisons niches humain animal]
  ([[humain _ . _]
    [_ animal . _] _ _])
  ([[_ humain . _]
    [animal _ . _] _ _])
  ([[_ . maisons-tail]
    [_ . niches-tail]_ _]
   (voisins-humain-animal maisons-tail niches-tail humain animal)))

(run* [maisons niches]
  ;; Règles de base
  (conditions-maisons maisons 3)
  (conditions-niches niches 3)

  ;; Règles de la carte
  (voisins maisons :vert :jaune)

  (place niches [:oiseau _ _])

  (voisins-humain-animal
   maisons niches
   :jaune :tortue)

  (place maisons [_ _ :vert]))
(check! #{(list [[:rouge :jaune :vert]
                 [:oiseau :lapin :tortue]])})

;; Carte 43
(run* [maisons]
  ;; Règles de base
  (conditions-maisons maisons 4)

  ;; Règles de la carte
  (est-a-gauche maisons :jaune :rouge)
  (place maisons [_ _ :blanc _])
  (voisins maisons :rouge :jaune))

(defne meme-maison [maisons niches humain animal]
  ([[humain . _]
    [animal . _] _ _])
  ([[a . maisons-tail]
    [b . niches-tail]_ _]
   (conde
    [(!= a humain)]
    [(!= b animal)])
   (meme-maison maisons-tail niches-tail humain animal)))

;; Carte 49
(run 1 [maisons niches]
  ;; Règles de base
  (conditions-maisons maisons 5)
  (conditions-niches niches 5)

  ;; Règles de la carte
  (place niches [_ _ _ :lapin _])
  (place maisons [:jaune _ _ _ _])
  (place niches [_ _ :chat _ _])

  (voisins-humain-animal
   maisons niches :blanc :lapin)
  (voisins-humain-animal
   maisons niches :rouge :chien)

  (meme-maison
   maisons niches :bleu :chien)

  (est-a-gauche niches :chat :tortue))
;; LONG !!!
;; => ([(:jaune :bleu :rouge :vert :blanc)
;;      (:oiseau :chien :chat :lapin :tortue)])
