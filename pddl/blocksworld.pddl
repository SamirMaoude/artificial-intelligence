(define (domain blocksworld)

(:requirements :strips :typing :negative-preconditions)

(:types
    block
    table
)

(:predicates
    (on-block ?b1 - block ?b2 - block)
    (on-table ?b - block ?t - table)
    (fixed ?b - block)
    (free ?t - table)
)


;déplacer un bloc b du dessus d'un bloc b1 vers le dessus d'un bloc b2
(:action move-block-to-block
    :parameters (?b - block ?b1 - block ?b2 - block)
    :precondition (and
                    (on-block ?b ?b1)
                    (not (fixed ?b))
                    (not (fixed ?b2))
    )
    :effect (and 
        
        (not (on-block ?b ?b1))
        (on-block ?b ?b2)
        (fixed ?b2)
        (not (fixed ?b1))
    )
)

;déplacer un bloc b du dessus d'un bloc b1 vers une table vide t,

(:action move-block-to-table
    :parameters (?b - block ?b1 - block ?t - table)
    :precondition (and
                    (on-block ?b ?b1)
                    (not (fixed ?b))
                    (free ?t)
    )
    :effect (and 
        
        (not (on-block ?b ?b1))
        (on-table ?b ?t)
        (not (free ?t))
        (not (fixed ?b1))
    )

)

;déplacer un bloc b du dessous d'une table t vers le dessus d'un bloc b1,
(:action move-from-table-to-block
    :parameters (?b - block ?t - table ?b1 - block)
    :precondition (and
                    (on-table ?b ?t)
                    (not (fixed ?b))
                    (not (fixed ?b1))
    )
    :effect (and 
        
        (not (on-table ?b ?t))
        (on-block ?b ?b1)
        (fixed ?b1)
        (free ?t)
    )
)

;déplacer un bloc b du dessous d'une table t vers une table vide t1.

(:action move-from-table-to-table
    :parameters (?b - block ?t - table ?t1 - table)
    :precondition (and
                    (on-table ?b ?t)
                    (free ?t1)
                    (not (fixed ?b))
    )
    :effect (and 
        
        (not (on-table ?b ?t))
        (on-table ?b ?t1)
        (not (free ?t1))
        (free ?t)
    )

)



)