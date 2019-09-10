parent(robert, robin).
parent(roberta, robin).
parent(robert, rosalie).
parent(roberta, rosalie).
parent(robert, roger).
parent(geraldine, roger).

parent(robert_father, robert).
parent(robert_father_father, robert_father).

ancestor(X, Y)
    :-  parent(X, Z), parent(Z, Y).
ancestor(X, Y)
    :-  parent(X, Z), ancestor(Z, Y). 