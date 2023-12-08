import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
//        Фамилия Имя Отчество датарождения номертелефона пол
//
//        Форматы данных:
//        фамилия, имя, отчество - строки
//        датарождения - строка формата dd.mm.yyyy
//        номертелефона - целое беззнаковое число без форматирования
//        пол - символ латиницей f или m.
//
//        Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
//
//        Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
//
//        Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида
//
//<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
//
//Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
//
//        Не забудьте закрыть соединение с файлом.
//
//        При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.


public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Введите данные в следующем формате: фамилия имя отчество, дата рождения, номертелефона, пол");
            String input = scanner.nextLine();

            String[] data = input.split(" ");

            if (data.length != 6) {
                System.out.println("Ошибка: неверное количество данных");
                return;
            }

            String surname = data[0];
            String name = data[1];
            String patronymic = data[2];
            String dateBirth = data[3];
            String phoneNumber = data[4];
            String gender = data[5];


            if (!dateBirth.matches("\\d{2}.\\d{2}.\\d{4}"))
                throw new InsufficientDataException("Вы ввели некорректную дату рождения!");


            if (!phoneNumber.matches("\\d+"))
                throw new InsufficientTelefonException("Вы ввели некорректный номер телефона!");


            if (!gender.matches("[fm]"))
                throw new InsufficientGenderException("Вы ввели неправильный формат пола:");
            System.out.println("Фамилия: " + surname);
            System.out.println("Имя: " + name);
            System.out.println("Отчество: " + patronymic);
            System.out.println("Дата рождения: " + dateBirth);
            System.out.println("Номер телефона: " + phoneNumber);
            System.out.println("Пол: " + (gender.equals("f") ? "Женский" : "Мужской"));
            String info = surname + name + patronymic + dateBirth + phoneNumber + gender;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("info.txt"))) {
                writer.write( info);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InsufficientDataException e) {
            System.out.println("Вы ввели некорректную дату рождения!");
        } catch (InsufficientTelefonException e) {
            System.out.println("Вы ввели некорректный номер телефона!");
        } catch (InsufficientGenderException e) {
            System.out.println("Вы ввели неправильный формат пола:");
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("info.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
