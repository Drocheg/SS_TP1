package jeuDeLaVie


class FlexibleRule1 : FlexibleRules("flexibleRule1",
        BooleanArray(27){i->when(i){
            7 -> false
            8 -> false
            9 -> false
            else -> true
    }},
        BooleanArray(27){i->when(i){
            3 -> true
            4 -> true
            5 -> true
            6 -> true
            7 -> true
            8 -> true
            9 -> true
            else -> false
        }})