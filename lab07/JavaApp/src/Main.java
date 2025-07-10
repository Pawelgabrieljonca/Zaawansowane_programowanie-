import java.util.Date;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        System.out.println("Witaj w grze RPG");
        int playerHealth = 10;
        int monsterHealth = 20;
        int attackPower = 1;

        for(int i = 1; i<=10;i++){


                System.out.println("Uderzasz potwora za " + attackPower + " obrażeń!");
                monsterHealth -= attackPower;

        }




    }
}