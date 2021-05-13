(ns meetup_crafters.prolog.logicville
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic
             :refer [run* == != membero lvar defne everyg fresh]]))

(defne couleur
  [c]
  ([c]
   (membero c [:jaune :rouge :vert])))

(defne sont_couleurs
  [l]
  ([l] (everyg couleur l)))

(defne tous_differents [l]
  ([[A B C]] (!= A B) (!= A C) (!= B C)))

(defne voisins [l a b]
  ([[a b _] _ _])
  ([[b a _] _ _])
  ([[_ a b] _ _])
  ([[_ b a] _ _]))

(defmacro solution_carte
  [card-name & rules]
  `(run* [~'l]
     (fresh [~'G ~'M ~'D]
       (== ~'l [~'G ~'M ~'D])
       ~@rules)
     (tous_differents ~'l)
     (sont_couleurs ~'l)))

(solution_carte
 "Carte 1"
 (== :vert D)
 (== :jaune G))

(solution_carte
 "Carte 2"
 (== :vert G)
 (!= :rouge M))

(solution_carte
 "Carte 3"
 (!= :vert M)
 (!= :jaune M)
 (!= :vert D))

(solution_carte
 "Carte 4"
 (!= :vert G)
 (!= :rouge M)
 (voisins l :rouge :jaune))

(solution_carte
 "Carte 5"
 (== :rouge G)
 (voisins l :vert :rouge))

(defne pas-voisins [l a b]
  ([[a _ b] _ _])
  ([[b _ a] _ _]))

(solution_carte
 "Carte 7"
 (== :rouge D)
 (pas-voisins l :rouge :vert))

(solution_carte
 "Carte 8"
 (pas-voisins l :rouge :vert)
 (!= :vert G))

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
