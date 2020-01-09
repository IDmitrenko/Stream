package ru.dias.predicate;

import java.util.ArrayList;
import java.util.List;

public class Main {

    // использование стримов при работе с коллекциями
    public static void main(String[] args) {

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Сергей",38));
        persons.add(new Person("Даша",7));
        persons.add(new Person("Глаша",3));
        persons.add(new Person("Саша",6));
        persons.add(new Person("Анна",18));

        // вывод коллекции обьектов на экран
        for (Person p : persons) {
            System.out.println(p);
        }

        // декларативный подход с использованием лямбда-выражений и стримов
        // 1 - вызываем у коллекции метод stream, который возвзращает нам обьект класса Stream
        // у класса Stream есть набор методов, которые в качестве параметров принимают
        // функциональные интерфейсы, а на их место мы можем подсунуть лямбда-выражения,
        // то есть функции. И эти функции будут выполняться для каждого обьекта этой коллекции.
        // 2 - у метода foreach параметр - это очередной обьект коллекции, а тело мы напишем
/*
        persons.stream().forEach(
                (Person p) -> {
                    System.out.println(p);
                });
*/
        // та же самая запись
        persons.stream().forEach(System.out::println);

        // фильтрация
        // вывод списка персон >= 18 лет
        // функциональный интерфейс Predicate - один параметр - очередной элемент коллекции
        // он возвращает булевское значение - истину или ложь
/*
        persons.stream().filter( p -> {
            return p.getAge() >= 18;
        }
        ).forEach( System.out::println);
*/
        // если единственным оператором в теле является return, то можно сократить запись
        persons.stream().filter( p -> p.getAge() >= 18 )
                .forEach( System.out::println );

        // сортировка
        persons.stream()
                .filter( p -> p.getAge() >= 18 )
        // функциональный интерфейс Comparator. Возвращает > 0, если первое значение больше второго;
        // и < 0, если первое значение меньше второго
        // сортируем по имени
                .sorted( (p1, p2) -> p1.getName().compareTo(p2.getName()))
        // map позволяет превратить исходный набор обьектов в другие данные
                // например - вывод только имен
                // на входе обьекты класса Person, на выходе имя (String)
                .map( p -> p.getName())
                .forEach( System.out::println );

        // хотим узнать средний возраст совершеннолетних людей в этом списке
        int summa = 0;
        int adultPersons = 0;
        for ( Person p : persons ) {
            if (p.getAge() >= 18) {
                summa += p.getAge();
                adultPersons++;
            }
        }

        double averageAge = (double) summa / adultPersons;
        System.out.println(averageAge);

        // решаем через стримы
        double average2 = persons.stream()
                .filter( p -> p.getAge() >= 18 )
                // возвращаем int возраст
                .mapToInt( p -> p.getAge() )
                // считаем среднее арифметическое типа double
                .average().getAsDouble();
        System.out.println(average2);
    }
}
