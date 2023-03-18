package P3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FriendshipGraph {

    private final HashMap<Person, HashSet<Person>> friends;

    public FriendshipGraph() {
        friends = new HashMap<Person, HashSet<Person>>();
    }

    //向关系图中添加顶点
    public boolean addVertex(Person per) {
        if (friends.containsKey(per)) {
            throw new IllegalArgumentException("输入名字重复!");
        }
        Set keys = friends.keySet();
        Iterator<Person> ie = keys.iterator();
        while (ie.hasNext()) {
            Person a = ie.next();
            if (per.getName().equals(a.getName())) {
                throw new IllegalArgumentException("输入名字重复!");
            }
        }
        friends.put(per, new HashSet<Person>());
        return true;
    }

    //在关系图中的两点间添加边
    public boolean addEdge(Person p1, Person p2) {
        if (!friends.containsKey(p1)) {
            throw new IllegalArgumentException("添加关系时输入的第一个人不在关系图中！");
        } else if (!friends.containsKey(p2)) {
            throw new IllegalArgumentException("添加关系时输入的第二个人不在关系图中！");
        } else if (friends.get(p1).contains(p2)) {
            System.out.println("该关系已经存在");
            return false;
        }
        friends.get(p1).add(p2);
        return true;
    }

    //求两点间距离并返回，若没有连接则返回-1
    public int getDistance(Person p1, Person p2) {
        if (!friends.containsKey(p1)) {
            throw new IllegalArgumentException("查找距离时输入的第一个人不在关系图中！");
        } else if (!friends.containsKey(p2)) {
            throw new IllegalArgumentException("查找距离时输入的第二个人不在关系图中！");
        }
        int distance = 0;
        HashSet<Person> already = new HashSet<>();//用于保存已经完成广度优先遍历的节点
        HashMap<Integer, HashSet<Person>> ver_d = new HashMap<>();//键值对分别为距离和在该距离下的点
        //初始化
        ver_d.put(0, new HashSet<Person>());
        ver_d.get(0).add(p1);
        //广度优先搜索
        do {
            if (ver_d.get(distance).contains(p2)) {
                return distance;
            }
            already.addAll(ver_d.get(distance));
            ver_d.put(distance + 1, new HashSet<Person>());
            for (Person i : ver_d.get(distance)) {
                if (friends.get(i).size() != 0) {
                    for (Person j : friends.get(i)) {
                        if (!already.contains(j)) {
                            ver_d.get(distance + 1).add(j);
                        }
                    }
                }
            }
            distance++;
        } while (ver_d.get(distance).size() != 0);
        //当循环退出还未搜索到，证明p1与p2不连接，返回-1
        return -1;
    }
}
