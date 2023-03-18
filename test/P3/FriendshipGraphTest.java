package P3;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FriendshipGraphTest {

    @Test
    public void addVertexTest() {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person rachel2 = new Person("Rachel");
        assertEquals(true, graph.addVertex(rachel));
        try {
            graph.addVertex(rachel);
        } catch (IllegalArgumentException e) {
            System.out.println("addVertexTest测试成功");
        }
        try {
            graph.addVertex(rachel2);
        } catch (IllegalArgumentException e) {
            System.out.println("addVertexTest测试成功");
        }
    }

    @Test
    public void addEdgeTest() {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        assertEquals(true, graph.addEdge(rachel, ross));
        assertEquals(true, graph.addEdge(ross, rachel));
        assertEquals(false, graph.addEdge(ross, rachel));
        try {
            graph.addEdge(rachel, ben);
        } catch (IllegalArgumentException e) {
            System.out.println("addEdgeTest测试成功");
        }
        try {
            graph.addEdge(ben, rachel);
        } catch (IllegalArgumentException e) {
            System.out.println("addEdgeTest测试成功");
        }
    }

    @Test
    public void getDistanceTest() {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        Person eee = new Person("eee");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        assertEquals(1, graph.getDistance(rachel, ross));
        assertEquals(2, graph.getDistance(rachel, ben));
        assertEquals(0, graph.getDistance(rachel, rachel));
        assertEquals(-1, graph.getDistance(rachel, kramer));
        try {
            graph.getDistance(rachel, eee);
        } catch (IllegalArgumentException e) {
            System.out.println("getDistanceTest测试成功");
        }
        try {
            graph.getDistance(eee, rachel);
        } catch (IllegalArgumentException e) {
            System.out.println("getDistanceTest测试成功");
        }

    }


    public static void main(String[] args) {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println(graph.getDistance(rachel, ross));
        //should print 1
        System.out.println(graph.getDistance(rachel, ben));
        //should print 2
        System.out.println(graph.getDistance(rachel, rachel));
        //should print 0
        System.out.println(graph.getDistance(rachel, kramer));
        //should print -1
    }
}