package jeuDeLaVie

interface GOLRule {
    fun modify(oldBoard: GOLBoard, newboard: GOLBoard, x: Int, y: Int, z: Int)
}