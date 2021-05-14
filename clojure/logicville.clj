(ns meetup_crafters.prolog.logicville
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic
             :refer [run* == != membero lvar defne everyg fresh matche distincto]]
            [clojure.core.logic.fd :as fd]
            [cognitect.transcriptor :as xr :refer (check!)]))

(defne couleur
  [c]
  ([c]
   (membero c [:jaune :rouge :vert])))

(defne sont_couleurs
  [houses]
  ([houses] (everyg couleur houses)))

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
     (fresh [~'G ~'M ~'D]
       (== ~'l [~'G ~'M ~'D]))
     (distincto ~'l)
     (sont_couleurs ~'l)
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
