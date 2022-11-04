package demo.kotlin.model.dijkstra

class Vert(name: String) : Comparable<Vert?> {
    private var visited = false
    private var name: String
    private var List: MutableList<Edge>
    private var dist = Double.MAX_VALUE
    private var pr: Vert? = null

    init {
        this.name = name
        List = ArrayList()
    }

    fun getList(): List<Edge> {
        return List
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setList(List: MutableList<Edge>) {
        this.List = List
    }

    fun addNeighbour(edge: Edge) {
        List.add(edge)
    }

    fun Visited(): Boolean {
        return visited
    }

    fun setVisited(visited: Boolean) {
        this.visited = visited
    }

    fun getPr(): Vert? {
        return pr
    }

    fun setPr(pr: Vert?) {
        this.pr = pr
    }

    fun getDist(): Double {
        return dist
    }

    fun setDist(dist: Double) {
        this.dist = dist
    }

    override fun toString(): String {
        return name
    }

    override fun compareTo(other: Vert?): Int {
        return dist.compareTo(other!!.dist)
    }
}
