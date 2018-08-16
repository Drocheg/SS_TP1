package jeuDeLaVie

interface GOLRule {

    val name: String

    fun modify(oldBoard: GOLBoard, newboard: GOLBoard, x: Int, y: Int, z: Int)
}