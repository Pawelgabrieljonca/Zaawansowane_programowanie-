import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
public class flatmap {

    public static class Person {
        private String nick;

        public Person(String nick) {
            this.nick = nick;
        }

        public String getNick() {
            return nick;
        }
        @Override
        public String toString() {
            return "Person{" +
                    "nick='" + nick + '\'' +
                    '}';
        }
    }
    public static class Group {
        private String groupName;
        private List<Person> members;

        public Group(String groupName, List<Person> members) {
            this.groupName = groupName;
            this.members = members;
        }

        public List<Person> getMembers() {
            return members;
        }

        public String getGroupName() {
            return groupName;
        }

        @Override
        public String toString() {
            return "Group{" +
                    "groupName='" + groupName + '\'' +
                    ", members=" + members +
                    '}';
        }
    }
    public static void main(String[] args) {
        List<Person> eaglesMembers = Arrays.asList(
                new Person("Orzeł1"),
                new Person("Orzeł2"),
                new Person("Orzeł3")
        );
        Group eagles = new Group("Eagles", eaglesMembers);

        List<Person> bikersMembers = Arrays.asList(
                new Person("Biker1"),
                new Person("Biker2"),
                new Person("Biker3"),
                new Person("Biker4")
        );
        Group bikers = new Group("Bikers", bikersMembers);
        List<Group> groups = Arrays.asList(eagles, bikers);
        List<Person> allMembers = groups.stream()
                .flatMap(group -> group.getMembers().stream())
                .collect(Collectors.toList());
        System.out.println("Wszyscy członkowie grup:");
        allMembers.forEach(System.out::println);
    }
}