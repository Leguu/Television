/*
 * Asil Erturan (40164714)
 * COMP249
 * Assignment #4
 * 2021-04-24
 */

import television.Show;
import television.ShowList;
import television.Watchable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException {
        var input = new Scanner(System.in);

        part1(input);
        part2(input);
    }

    static void part2(Scanner input) throws FileNotFoundException {
        ShowList list1 = new ShowList();
        ShowList list2;

        System.out.print("Input the TV Guide file path: \n> ");
        var guideReader = new Scanner(new FileInputStream(input.nextLine()));
//        var guideReader = new Scanner(new FileInputStream("material/TVGuide.txt"));
        var watching = new ArrayList<String>();
        process(guideReader, list1);
        guideReader.close();

        System.out.print("Input the interests file path: \n> ");
        var interestReader = new Scanner(new FileInputStream(input.nextLine()));
//        var interestReader = new Scanner(new FileInputStream("material/Interest.txt"));
        var wishlist = new ArrayList<String>();
        // Skip the first line
        var line = interestReader.nextLine();
        // Input the currently watching values
        while (interestReader.hasNextLine()) {
            line = interestReader.nextLine();
            if (line.equals("Wishlist")) break;
            watching.add(line);
        }
        // Input the wishlist values
        while (interestReader.hasNextLine()) {
            line = interestReader.nextLine();
            if (line.isBlank()) break;
            wishlist.add(line);
        }
        interestReader.close();

        for (String wishid : wishlist) {
            var wish = list1.find(wishid);
            var compatible = true;
            for (String watchid : watching) {
                var watch = list1.find(watchid);

                if (wish.compatible(watch) != Watchable.Compatibility.DIFFERENT) {
                    System.out.println("User cannot watch "
                            + wish.name() + " (" + wish.id() + ") because they are watching "
                            + watch.name() + " (" + watch.id() + ")" + " at the time.");
                    compatible = false;
                }
            }
            if (compatible) System.out.println("User can watch " + wish.name() + " (" + wish.id() + ")");
        }

        System.out.print("Input show IDs to display information: \n> ");
        line = input.nextLine();
        while (!line.isBlank()) {
            var show = list1.find(line);

            if (show != null) System.out.println("Show is: " + show);
            else System.out.println("Show does not exist...");

            System.out.print("> ");
            line = input.nextLine();
        }
        System.out.println("Empty line detected, continuing...");

        // Demonstrate copy constructor abilities...
        list2 = new ShowList(list1);
        for (int i = 0; i < list2.size(); i += 1) {
            var node1 = list1.get(i);
            var node2 = list2.get(i);
            var different = node1.equals(node2) && (node1 != node2);
            System.out.print("Node: " + node1 + ". ");
            System.out.println("These two nodes are different: " + different);
        }

        list1.insert(new Show("a", "b", 2, 2), 3);
        System.out.println(list1.get(3));
        list1.remove(3);
    }

    static void part1(Scanner input) throws FileNotFoundException {
        System.out.print("Input text file path: \n> ");
        // var reader = new Scanner(new FileInputStream("material/history_of_java.txt"));
        var reader = new Scanner(new FileInputStream(input.nextLine()));
        parser(reader);
    }

    // Process a file and input them into the List
    static void process(Scanner reader, ShowList list) {
        while (reader.hasNextLine()) {
            // ID and name
            var header = reader.nextLine().split(" ");
            // The start time
            var start = reader.nextLine().split(" ")[1];
            // The end time
            var end = reader.nextLine().split(" ")[1];
            reader.nextLine(); // skip the last newline

            var show = new Show(header[0], header[1], Double.parseDouble(start), Double.parseDouble(end));

            // Skip duplicate shows
            if (!list.contains(show.id())) list.prepend(show);
        }
    }


    //
    // Part One
    //

    /**
     * Number of vowels in a string.
     */
    static long vowelCount(String word) {
        return word.chars().filter(c -> {
            c = Character.toLowerCase(c);
            return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
        }).count();
    }

    static void parser(Scanner reader) throws FileNotFoundException {
        var vowel = new PrintWriter("output/vowel_verbiage.txt");
        var obsessive = new PrintWriter("output/obsessive_o.txt");
        var distinct = new PrintWriter("output/distinct_data.txt");

        var words = new ArrayList<String>();
        while (reader.hasNext()) words.add(reader.next().replaceAll("[^a-zA-Z0-9]", ""));

        var vowels = new ArrayList<String>();
        var obsessives = new ArrayList<String>();
        var distincts = new ArrayList<String>();

        for (String word : words) {
            if (vowelCount(word) > 3) vowels.add(word);
            if (word.toLowerCase().startsWith("o")) obsessives.add(word);
            if (!distincts.contains(word)) distincts.add(word);
        }

        vowel.println("Word Count: " + vowels.size());
        for (String s : vowels) vowel.println(s);

        obsessive.println("Word Count: " + obsessives.size());
        for (String s : obsessives) obsessive.println(s);

        distinct.println("Word Count: " + distincts.size());
        for (String s : distincts) distinct.println(s);

        vowel.close();
        obsessive.close();
        distinct.close();
    }
}
