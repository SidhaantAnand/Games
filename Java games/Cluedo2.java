import java.util.*;

/**
 * Created by Sidhaant on 7/17/2017.
 */

/*

The famous Cluedo or Clue is a detective game where you play against the computer to find who,with what and where was Mr.Black killed

Explanation of Code/Rules of the game:
1) The guilty cards are randomly picked by using Random class and added to the static Stack correctchoice. Thus all objects of the
   class have one copy of Stack  correctchoice

2) Other global variable is a HashMap of Key of type String and Value of type Boolean. If the Value is true then the key is in your
   suspect list otherwise it's free from suspection. Value is declared false when the computer shows you a card when you make an accusation.
   The card which the computer shows is printed on the console. This change of value happens in the HashMap stored
   in object "objUserSuspects" (read point 4)

3) Youll be given a set of cards which are all guilt free. You will show one card to the computer when the computer makes his accusation
   The computer's choices will be displayed on the screen. You can strategically show one card. The Value of the card(key) you show
   will be made false in the HashMap stored in object "compSuspects"

4) What happens in object "objUserSuspects". It stores the guilty cards and the computer cards. Thus you make accusation from amongst the
   keys of the HashMap in objUserSuspects whose values are true.

 */
public class Cluedo2 {

    /*NOTE: You will notice that on the console the correct choices have been printed
            This has been done for your convenience so that you can test the code quickly
            When other people play, the correct choices are hidden
     */

    // cluedo is an array of data type HashMaps
    // HashMap have Key as String and value as Boolean
    private HashMap<String, Boolean> cluedo[];
    private static Stack<String> correctchoice = new Stack<>(); // all objects have only one copy of correctchoice


    Cluedo2() {

        cluedo = new HashMap[3];
    }

    public void put(ArrayList<String> in, int index) {

        HashMap<String, Boolean> input = new HashMap<>();
        for (int i = 0; i < in.size(); i++) {
            input.put(in.get(i), true);
        }

        cluedo[index] = input;
    }

    private void intro(Cluedo2 objUserSuspects) {
        System.out.println("Hello Player\nYou will be playing against the computer to find out who killed Mr.Black with what weapon and in which room");

        System.out.println("\nHere are your cards\nThey following are not guilty,the weapon used or the room killed in");
        System.out.println("These are your cards which you will reveal one by one when the computer makes his turn\n");

        this.displayAllKeys();

        System.out.println("\nThe culprit,the weapon used and the room are hidden in the following cards\n");

        objUserSuspects.displayAllKeys();

    }

    private void displayAllKeys() {


        for (int i = 0; i < this.cluedo.length; i++) {

            Set<Map.Entry<String, Boolean>> entries = this.cluedo[i].entrySet();
            for (Map.Entry<String, Boolean> entry : entries) {
                System.out.print(entry.getKey() + "  ");

            }

            System.out.println("\n");
        }
    }

    private void randomlyChoosingTheGuiltyCards() {
        System.out.println("correct choices");

        for (int i = 0; i < cluedo.length; i++) {

            LinkedList<String> ob = new LinkedList<>();
            Set<Map.Entry<String, Boolean>> entries = cluedo[i].entrySet();
            for (Map.Entry<String, Boolean> entry : entries)
                ob.add(entry.getKey());

            Random rn = new Random();
            int rand = rn.nextInt(3);
            correctchoice.push(ob.get((int) rand));
            System.out.println(ob.get((int) rand));

        }
    }

    private boolean storingChoice(Cluedo2 objUserSuspects) {

        System.out.println("It is your turn to make an accusation");
        Stack<String> store = new Stack<String>();

        Scanner scrn = new Scanner(System.in);

        System.out.println("Enter your choices in the order of suspects,weapons and rooms respectively\n");

        System.out.println("The remaining list of suspects are");
        objUserSuspects.remaining(cluedo[0]);
        store.push(scrn.next());
        System.out.println("\n");

        System.out.println("The remaining list of weapons are");
        objUserSuspects.remaining(cluedo[1]);
        store.push(scrn.next());
        System.out.println("\n");

        System.out.println("The remaining list of rooms are");
        objUserSuspects.remaining(cluedo[2]);
        store.push(scrn.next());
        System.out.println("\n");

        return (UsersTurn(store));

    }

    private boolean UsersTurn(Stack<String> store) {
        Stack<String> restore = new Stack<>();
        boolean found = true;

        boolean done = false;

        outer:  for (int i = 0; i < correctchoice.size(); i++) {

            String val1 = store.pop();
            String val2 = correctchoice.pop();
            restore.push(val2);


            if (val1.equals(val2)) {

                continue outer;
            } else if (done == false) {

                found = false;
                int index = i;

                Set<Map.Entry<String, Boolean>> entries = cluedo[cluedo.length - 1 - i].entrySet();
                for (Map.Entry<String, Boolean> entry : entries) {

                    if (entry.getKey().equals(val1)) {
                        found = false;
                        entry.setValue(false);
                        System.out.println("The computer showed you the card " + entry.getKey() + " this has automatically been remove from your suspects list");
                        System.out.println("\n");
                        done = true;
                        i = index;

                    }
                }
            }
        }

        if(done == false && found == true)
        {
            String val1 = store.pop();
            String val2 = correctchoice.pop();
            restore.push(val2);

            if(val1.equals(val2) == true)
                found = true;

            else
            {
                Set<Map.Entry<String, Boolean>> entries = cluedo[0].entrySet();
                for (Map.Entry<String, Boolean> entry : entries) {

                    if (entry.getKey().equals(val1)) {
                        found = false;
                        entry.setValue(false);
                        System.out.println("The computer showed you the card " + entry.getKey() + " this has automatically been remove from your suspects list");

                    }
                }

            }

        }

        while(restore.size() != 0)
        {
            String val = restore.pop();
            correctchoice.push(val);

        }


        return found;
    }

    private void remaining(HashMap<String, Boolean> hm) {
        Set<Map.Entry<String, Boolean>> entries = hm.entrySet();
        for (Map.Entry<String, Boolean> entry : entries)
            if (entry.getValue() == true)
                System.out.print(entry.getKey() + " ");
        System.out.println();
    }

    private boolean CompTurn() {

        System.out.println("It's the computers turn to make an accusation\n");

        Stack<String> store = new Stack<>();
        for (int i = 0; i < this.cluedo.length; i++) {

            LinkedList<String> ob = new LinkedList<>();

            Set<Map.Entry<String, Boolean>> entries = cluedo[i].entrySet();

            for (Map.Entry<String, Boolean> entry : entries) {

                ob.add(entry.getKey());
            }


            inner:
            while (true) {
                Random rn = new Random();
                int rand = rn.nextInt(cluedo[i].size());

                if (cluedo[i].get(ob.get(rand)) == false)
                    continue inner;
                else {
                    store.push(ob.get(rand));
                    break;
                }

            }
        }

        System.out.println("The computer made the following accusation. If you have one of the cards please show it to the computer else enter \"None\"\n");

        Stack<String>delete = new Stack<>();

        while(store.size() != 0)
        {
            String val = store.pop();
            System.out.println(val);
            delete.push(val);
        }

        Scanner scrn2 = new Scanner(System.in);
        String scrn = scrn2.next();

        while(delete.size() !=0)
            store.push(delete.pop());


        boolean result = this.comCheck(store);

        if (result == true)
            return true;

        else {


            for (int i = 0; i < this.cluedo.length; i++) {

                Set<Map.Entry<String, Boolean>> entries = cluedo[i].entrySet();

                for (Map.Entry<String, Boolean> entry : entries) {
                    if (scrn.equals(entry.getKey()))
                        entry.setValue(false);
                }

            }

            return false;
        }
    }


    private void compSuspects(Cluedo2 objuser, Cluedo2 objUserSuspects) {

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> weapons = new ArrayList<String>();
        ArrayList<String> rooms = new ArrayList<String>();

        Set<Map.Entry<String, Boolean>> entriesnames = objuser.cluedo[0].entrySet();
        for (Map.Entry<String, Boolean> entry : entriesnames)
            names.add(entry.getKey());

        Set<Map.Entry<String, Boolean>> entriesweapons = objuser.cluedo[1].entrySet();
        for (Map.Entry<String, Boolean> entry : entriesweapons)
            weapons.add(entry.getKey());

        Set<Map.Entry<String, Boolean>> entriesrooms = objuser.cluedo[2].entrySet();
        for (Map.Entry<String, Boolean> entry : entriesrooms)
            rooms.add(entry.getKey());

        Stack<String> restore = new Stack<>();

        Set<Map.Entry<String, Boolean>> entriesrooms2 = objUserSuspects.cluedo[2].entrySet();
        String room = correctchoice.pop();
        for (Map.Entry<String, Boolean> entry : entriesrooms2) {
            if (entry.getKey().equals(room) == true)
                rooms.add(entry.getKey());
        }
        restore.push(room);

        Set<Map.Entry<String, Boolean>> entriesweapons2 = objUserSuspects.cluedo[1].entrySet();
        String weapon = correctchoice.pop();
        for (Map.Entry<String, Boolean> entry : entriesweapons2) {
            if (entry.getKey().equals(weapon) == true)
                weapons.add(entry.getKey());
        }
        restore.push(weapon);

        Set<Map.Entry<String, Boolean>> entriesnames2 = objUserSuspects.cluedo[0].entrySet();
        String name = correctchoice.pop();
        for (Map.Entry<String, Boolean> entry : entriesnames2) {
            if (entry.getKey().equals(name) == true)
                names.add(entry.getKey());
        }
        restore.push(name);

        while (restore.size() != 0)
            correctchoice.push(restore.pop());

        this.put(names, 0);
        this.put(weapons, 1);
        this.put(rooms, 2);


    }

    private boolean comCheck(Stack<String> store)
    {
        boolean flag = true;
        Stack<String> restore = new Stack<>();

        while(store.size() != 0)
        {
            String val = correctchoice.pop();
            restore.push(val);

            if(val.equals(store.pop()) == false)
                flag = false;
        }

        while(restore.size() != 0)
            correctchoice.push(restore.pop());

        if(flag)
            return true;
        else
            return false;
    }

    public static void main(String[] args) {

        Cluedo2 objuser = new Cluedo2();
        Cluedo2 objUserSuspects = new Cluedo2();
        Cluedo2 objcompSuspects = new Cluedo2();

        ArrayList<String> suspects1 = new ArrayList<String>(Arrays.asList("Col.Mustard", "Ms.Peacock"));
        ArrayList<String> rooms1 = new ArrayList<String>(Arrays.asList("Ballroom", "Patio"));
        ArrayList<String> weapons1 = new ArrayList<String>(Arrays.asList("Knife", "Trophy"));

        objuser.put(suspects1, 0);
        objuser.put(weapons1, 1);
        objuser.put(rooms1, 2);

        ArrayList<String> suspects2 = new ArrayList<String>(Arrays.asList("Prof.Plum", "Rev.Green", "Ms.Red"));
        ArrayList<String> rooms2 = new ArrayList<String>(Arrays.asList("Kitchen", "Hall", "Dining"));
        ArrayList<String> weapons2 = new ArrayList<String>(Arrays.asList("Candlestick", "Bat", "Poison"));

        objUserSuspects.put(suspects2, 0);
        objUserSuspects.put(weapons2, 1);
        objUserSuspects.put(rooms2, 2);

        objuser.intro(objUserSuspects);

        objUserSuspects.randomlyChoosingTheGuiltyCards();

        objcompSuspects.compSuspects(objuser, objUserSuspects);

        objcompSuspects.displayAllKeys();

        outer:
        while (true) {

            boolean founduser = objUserSuspects.storingChoice(objUserSuspects);

            if (founduser == true) {
                System.out.println("Congratulations the user won. The gulity cards were ");
                while (correctchoice.size() != 0)
                    System.out.println(correctchoice.pop());
                break outer;
            } else {
                boolean foundcomp = objcompSuspects.CompTurn();
                // boolean foundcomp = false;

                if (foundcomp == true) {
                    System.out.println("Uh Oh.Looks like the computer beat you to it. The gulity cards were ");
                    while (correctchoice.size() != 0)
                        System.out.println(correctchoice.pop());
                    break outer;
                } else
                    continue outer;


            }


        }
    }
}

