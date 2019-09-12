couleur(jaune).
couleur(rouge).
couleur(vert).

sont_couleurs([]).
sont_couleurs([H|L]) :-
    couleur(H),
    sont_couleurs(L).

all_differents(A, B, C) :-
    not(A = B),
    not(B = C),
    not(A = C).

voisins([A, B, C], X, Y) :-
    once(
        (A = X, B = Y);
        (A = Y, B = X);
        (C = X, B = Y);
        (C = Y, B = X)
    ).

pas_voisins([A, B, C], X, Y) :-
% https://www.swi-prolog.org/pldoc/doc_for?object=not/1
% Retained for compatibility only. New code should use \+/1.
    not(voisins([A, B, C], X, Y)).
/**
est_a_gauche(L, X, Y) :-
    once(
        (L = [X, Y, _]);
        (L = [X, _, Y]);
        (L = [_, X, Y])
    ).
*/
est_a_gauche([X, Y, _], X, Y).
est_a_gauche([X, _, Y], X, Y).
est_a_gauche([_, X, Y], X, Y).

solution_carte_1([G, M, D]) :-
    sont_couleurs([G, M, D]),
    all_differents(G, M, D),
    vert = D,
    jaune = G.

solution_carte_2([G, M, D]) :-
    sont_couleurs([G, M, D]),
    all_differents(G, M, D),
    vert = G,
    different(rouge, M).

solution_carte_3([G, M, D]) :-
    sont_couleurs([G, M, D]),
    all_differents(G, M, D),
    different(vert, M),
    different(jaune, M),
    different(vert, D).

solution_carte_4([G, M, D]) :-
    sont_couleurs([G, M, D]),
    all_differents(G, M, D),
    different(vert, G),
    different(rouge, M),
    voisins([G, M, D], rouge, jaune).

solution_carte_5([G, M, D]) :-
    sont_couleurs([G, M, D]),
    all_differents(G, M, D),
    rouge = G,
    voisins([G, M, D], vert, rouge).

% Bypass 6, trop facile.

solution_carte_7([G, M, D]) :-
    sont_couleurs([G, M, D]),
    all_differents(G, M, D),
    rouge = D,
    pas_voisins([G, M, D], rouge, vert).

solution_carte_8([G, M, D]) :-
    sont_couleurs([G, M, D]),
    all_differents(G, M, D),
    pas_voisins([G, M, D], rouge, vert),
    not(vert = G).

solution_carte_9([G, M, D]) :-
    sont_couleurs([G, M, D]),
    all_differents(G, M, D),
    voisins([G, M, D], rouge, jaune),
    voisins([G, M, D], jaune, vert),
    est_a_gauche([G, M, D], vert, rouge).

