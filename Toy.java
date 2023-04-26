import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Toy {
    private String id;
    private String name;
    private double frequency;


     public Toy(String id, String name, double frequency) {
        this.id = id;
        this.name = name;
        this.frequency = frequency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Toy{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", frequency=" + frequency +
                '}';
    }

    public static void main(String[] args) {
        String[] input = {
                "1 2 конструктор",
                "2 2 робот",
                "3 6 кукла"
        };
        Queue<Toy> toyQueue = new PriorityQueue<>(Comparator.comparing(Toy::getFrequency).reversed());

        for (String s : input) {
            String[] tokens = s.split(" ");
            String id = tokens[0];
            double frequency = Double.parseDouble(tokens[1]) / 10.0;
            String name = tokens[2];
            toyQueue.offer(new Toy(id, name, frequency));
        }

        Queue<Toy> globalQueue = new PriorityQueue<>(Comparator.comparing(Toy::getFrequency).reversed());

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            double r = random.nextDouble();

            if (r < 0.2) {
                globalQueue.offer(toyQueue.peek());
            } else if (r < 0.4) {
                Toy[] toys = new Toy[2];
                for (int j = 0; j < 2; j++) {
                    toys[j] = toyQueue.poll();
                }
                globalQueue.addAll(Arrays.asList(toys));
            } else {
                Toy[] toys = new Toy[3];
                for (int j = 0; j < 3; j++) {
                    toys[j] = toyQueue.poll();
                }
                globalQueue.addAll(Arrays.asList(toys));
            }
        }

        try (PrintWriter writer = new PrintWriter(new File("output.txt"))) {
            for (int i = 0; i < 10; i++) {
                Toy toy = globalQueue.remove();
                writer.println(toy.getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}