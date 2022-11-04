package demo.kotlin.model.dijkstra

data class Edge(
    var weight : Double,
    var startVert: Vert,
    var targetVert : Vert
)