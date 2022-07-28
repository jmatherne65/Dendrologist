package dendrologist;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Function;

/**
 * A testbed for an augmented implementation of an AVL tree
 * @author William Duncan, James Matherne
 * @see AVLTreeAPI
 * <pre>
 * Date: 06/19/2022
 * CSC 3102 Programming Project # 1
 * Instructor: Dr. Duncan 
 * </pre>
 */
public class Dendrologist {
    public static void main(String[] args) throws FileNotFoundException {
        String usage = "Dendrologist <order-code> <command-file>\n";
        usage += "  <order-code>:\n";
        usage += "  0 ordered by increasing string length, primary key, and reverse lexicographical order, secondary key\n";
        usage += "  -1 for reverse lexicographical order\n";
        usage += "  1 for lexicographical order\n";
        usage += "  -2 ordered by decreasing string\n";
        usage += "  2 ordered by increasing string\n";
        usage += "  -3 ordered by decreasing string length, primary key, and reverse lexicographical order, secondary key\n";
        usage += "  3 ordered by increasing string length, primary key, and lexicographical order, secondary key\n";
        if (args.length != 2) {
            System.out.println(usage);
            throw new IllegalArgumentException("There should be 2 command line arguments.");
        }
        Function<String,PrintStream> fn = (x) -> System.out.printf("%s\n", x);
        File f = new File("strings.avl");
        Scanner fileReader = new Scanner(f);
        Comparator<String> cmp = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        };
        if (args[0].equals("0")) {
            cmp = (str1, str2) -> {
                if (str1.length() > str2.length()) {
                    return 1;
                } else if (str1.length() < str2.length()) {
                    return -1;
                } else if (str1.length() == str2.length()) {
                    return str2.compareTo(str1);
                }
                return str2.compareTo(str1);
            };
        }
        if (args[0].equals("-1")) {
            cmp = Comparator.reverseOrder();
        }
        if (args[0].equals("1")){
            cmp = (str1, str2) -> str1.compareTo(str2);
        }
        if (args[0].equals("-2")) {
            cmp = (str1, str2) -> {
                if (str1.length() < str2.length()) {
                    return 1;
                } else if (str1.length() > str2.length()) {
                    return -1;
                } else
                return 0;
            };
        }
        if (args[0].equals("2")) {
            cmp = (str1, str2) -> {
                if (str1.length() > str2.length()) {
                    return 1;
                } else if (str1.length() < str2.length()) {
                    return -1;
                } else
                return 0;
            };
        }
        if (args[0].equals("-3")) {
            cmp = (str1, str2) -> {
                if (str1.length() < str2.length()) {
                    return 1;
                } else if (str1.length() > str2.length()) {
                    return -1;
                } else
                    return str2.compareTo(str1);
            };
        }
        if (args[0].equals("3")){
            cmp = (str1, str2) -> {
                if (str1.length() > str2.length()){
                    return 1;
                } else if (str1.length() < str2.length()){
                    return -1;
                } else
                return str1.compareTo(str2);
            };
        }
        AVLTree<String> tree = new AVLTree<>(cmp);
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] statement = line.split(" ");
            if (statement[0].equals("stats")) {
                System.out.println("Stats: size = " + tree.size() + ", height = " + tree.height() + ", diameter = " + tree.diameter() + ", #leaves = " + tree.leafCount() + ", perfect? = " + tree.isPerfect());
            } else if (statement[0].equals("insert")) {
                tree.insert(statement[1]);
                System.out.println("Inserted: " + statement[1]);
            } else if (statement[0].equals("traverse")){
                if (statement[1].equals("-0")){
                    System.out.println("In-Order Traversal: ");
                    tree.traverse(fn);
                }else if (statement[1].equals("-1")){
                    System.out.println("Level-Order Traversal: ");
                    tree.levelOrder(fn);
                }else if (!statement[1].equals("-1") || !statement[1].equals("-1")) {
                    System.out.println("Parsing Error");
                    try {
                        throw new Exception("traverse<" + statement[1] + ">: traversalCode must be -0 or -1");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (statement[0].equals("delete")) {
                tree.remove(statement[1]);
                System.out.println("Deleted: " + statement[1]);
            }
        }
        }
    }
