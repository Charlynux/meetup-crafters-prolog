# Meetup Software Crafters - Amiens - A la découverte de Prolog

## Installation du REPL

```
brew install swi-prolog
```

## Lancement du REPL

```
swipl
```

## Exemples

### Quelques faits

```
[1, 2, 3] = [1, 2, 3].
```

```
append([1], [2], [1, 2]).
```

```
sum_list([1, 2, 3], 6).
```

### Quelques interrogations

```
[1, 2, 3] = [1, 2, A].
```

```
append([1], L2, [1, 2]).
```

```
sum_list([1, 2, 3], Sum).
```

```
append(L1, L2, [1, 2]).
```

Tapez `;` ou Espace pour avoir les différentes solutions une à une.

### Création de nouveaux faits

```
['parents'].
```

```
parent(robert, robin).
```

```
parent(robert, A).
```

```
parent(robert, A), parent(geraldine, A).
```

### Rule récursive

```
['length'].
```

```
list_length([1, 2, 3, 4], Taille).
```

Ne marche pas...

```
list_length(Liste, 4).
```

### Combinaison de facts et rules

```
['ancestors'].
```

```
ancestor(robert_father, robin).
```

```
ancestor(robert_father, A).
```

```
ancestor(robert_father_father, A).
```

```
ancestor(A, robin).
```

## Exemples

### Représentation visuelle

Pour le problème des N Dames, représentation d'un échiquier en Prolog.

https://swish.swi-prolog.org/example/queens.pl

### Interpréteur Prolog

La première version du langage Erlang était écrite en Prolog.

http://blog.erlang.org/beam-compiler-history#the-prolog-interpreter

### Yarn V2

Pour sa v2, Yarn intègre un système de contraintes qui s'appuie sur Prolog.

https://next.yarnpkg.com/features/constraints

https://youtu.be/1Bk8-1ikXiU?t=1543

Merci à Sébastien Elet (@SebastienElet) pour cette information
