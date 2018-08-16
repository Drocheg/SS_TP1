package jeuDeLaVie

class FlexibleRule4555 : FlexibleRules("flexibleRule4555",
        BooleanArray(27){i->when(i){
            4 -> false
            5 -> false
            else -> true
    }},
        BooleanArray(27){i->when(i){
            5 -> true
            else -> false
        }})