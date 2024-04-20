Program Execution Sequence
---------------------------
        
        +-------------------+
        |        main       |
        +--------+----------+
                 |
        +--------v----------+
        |       Frame       |
        +--------+----------+
                 |
        +--------v----------+
        |       Panel       |
        +--------+----------+
                 |
       +---------v-----------+
       |     paintComponent  |
       |     button(generate)|
       |     button(sort)    |
       +---------+-----------+
                 |
       +---------v----------+
       |     ActionListener |
       | button(generate) ->| -> generateList() -> initialize array -> repaint
       | button(sort) ->    | -> sortList() -> swap -> repaint -> sleep -> stop
       +---------+----------+
