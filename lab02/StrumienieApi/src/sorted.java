import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class sorted {
    public static class Person{
        private String nick;
        private int age;

        public Person(String nick, int age) {
            this.nick = nick;
            this.age = age;
        }
        public String getNick() {

            return nick;
        }

        public int getAge() {
            return age;
        }
        @Override
        public String toString() {
            return "Person{" + "nick=" + nick + ", age=" + age + '}';
        }
    }
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Daniel", 18));
        people.add(new Person("Ewa", 101));
        people.add(new Person("Anna", 13));
        people.add(new Person("Daniel", 10));

        people.stream().sorted(Comparator.comparing(Person::getNick).thenComparing(Person::getAge))
                .forEach(System.out::println);
    }

}
