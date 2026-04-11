(define (problem blocksworld-problem)
    (:domain blocksworld)
    (:objects
        B0 B1 B2 B3 B4 B5 B6 B7 B8 B9 B10 B11 - block
        T1 T2 T3 T4 - table
    )
    (:init
        (on-table B0 T1)
        (on-block B1 B0)
        (on-block B9 B1)
        (on-block B8 B9)

        (on-table B6 T2)
        (on-block B10 B6)
        (on-block B2 B10)

        (on-table B7 T3)
        (on-block B3 B7)
        (on-block B4 B3)
        (on-block B5 B4)

        (on-table B11 T4)

        (fixed B0)
        (fixed B1)
        (fixed B3)
        (fixed B4)
        (fixed B6)
        (fixed B7)
        (fixed B9)
        (fixed B10)



    )
    (:goal
        (and
            
            (on-table B4 T1)
            (on-table B5 T2)


            (on-table B6 T3)
            (on-block B10 B6)
            (on-block B2 B10)
            (on-block B0 B2)
            (on-block B1 B0)
            (on-block B9 B1)
            (on-block B8 B9)
            (on-block B7 B8)
            (on-block B3 B7)


            (on-table B11 T4)
        )
    )
)
