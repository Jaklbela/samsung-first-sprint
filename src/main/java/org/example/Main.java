package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String castle = "^";
        int sizeBoard = 5;
        int step = 0;
        int castleX;
        int castleY;

        Person person = new Person(0, sizeBoard - 1);

        Random random = new Random();
        castleX = random.nextInt(sizeBoard - 1);
        castleY = 0;

        String[][] board = new String[sizeBoard][sizeBoard];

        Monster monster = new Monster();
        Monster bigMonster = new BigMonster();

        for (int y = 0; y < sizeBoard; y++) {
            for (int x = 0; x < sizeBoard; x++) {
                board[y][x] = " ";
            }
        }
        for (int i = 0; i < sizeBoard * 2; i++) {
            if (random.nextBoolean()) {
                board[random.nextInt(sizeBoard - 2)]
                        [random.nextInt(sizeBoard - 1)] = monster.getImage();
            } else {
                board[random.nextInt(sizeBoard - 2)]
                        [random.nextInt(sizeBoard - 1)] = bigMonster.getImage();
            }
        }

        board[castleY][castleX] = castle;
        board[person.getY()][person.getX()] = person.getImage();

        Scanner scanner = new Scanner(System.in);

        do {
            drawBoard(board);

            System.out.println("Введите направление хода:\n" +
                    "0 - влево вверх\n" +
                    "1 - вверх\n" +
                    "2 - вправо вверх\n" +
                    "3 - влево\n" +
                    "4 - вправо\n" +
                    "5 - влево вниз\n" +
                    "6 - вниз\n" +
                    "7 - вправо вниз\n");
            int choice = scanner.nextInt();
            int x = person.getX();
            int y = person.getY();
            if (person.move(choice, sizeBoard)) {
                if (board[person.getY()][person.getX()].equals("#")) {
                    int t = monster.taskGenerator(person);
                    person.setLives(t);
                } else if (board[person.getY()][person.getX()].equals("!")) {
                    int t = bigMonster.taskGenerator(person);
                    person.setLives(t);
                } else if (board[person.getY()][person.getX()].equals("^")) {
                    System.out.println("Поздравляю! Вы выиграли.");
                    break;
                }
                board[y][x] = " ";
                board[person.getY()][person.getX()] = "*";
                step++;
            } else {
                System.out.println("Некорретный ход. Попробуйте снова");
            }
        } while (person.getLives() > 0);
        System.out.println("Ваш счет: " + step);
    }

    static void drawBoard(String[][] board) {
        String block = "| ";
        String wall = "+ — + — + — + — + — +";

        for (String[] row : board) {
            System.out.println(wall);

            for (String cell : row) {
                System.out.print(block + cell + " ");
            }

            System.out.println(block);
        }
        System.out.println(wall);
    }
}