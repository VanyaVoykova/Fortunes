import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Fortunes {

    public static void main(String[] args) {
        fortuneTeller();

    }


    public static void fortuneTeller() {

        JFrame frame = new JFrame("Fortunes");
        //set size and location
        frame.setSize(400, 300);
        frame.setLocation(100, 150);

        //Exit when X is clicked
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setDefaultLookAndFeelDecorated(true); //default as system

        //Enter the names of the people waiting for their fortune:
        //The <html> tags are to make the text wrap. (Since Java 7)
        JLabel namesLabel = new JLabel("<html>" + "Въведете имената на очакващите късмета си (разделени със запетая):" + "</html>");
        namesLabel.setBounds(50, 50, 300, 40);
        JTextField namesInput = new JTextField();
        namesInput.setBounds(50, 100, 300, 30);

        //add the above elements to the frame
        frame.add(namesLabel);
        frame.add(namesInput);
        frame.setLayout(null); //null -> no LayoutManager, absolute position, can drag frame
        frame.setVisible(true);

        JButton submitBtn = new JButton("Изтегли късмети");
        submitBtn.setBounds(50, 150, 300, 30);
        frame.add(submitBtn);

        //Prep resultsOutput visualization
        List<String> names = new ArrayList<>();


        //NOTE: All this has to be tied within the event, so it can update properly; putting it in a method that returns a list screws up the flow and the vent?!?
        submitBtn.addActionListener(event -> {
            String[] namesInputArr = Arrays
                    .stream(namesInput.getText().split("[, ]+"))
                    .toArray(String[]::new);

            namesInput.setText("");//clear input

            names.addAll(Arrays.stream(namesInputArr).toList());
            names.removeAll(Arrays.asList("", null)); //trim empty strings

            if (!names.isEmpty()) {
                displayResults(namesLabel, namesInput, submitBtn, names, frame);
            }

        });


    }

    //TODO: Scrollable results pane to fit in many people's fortunes
    private static void displayResults(JLabel namesLabel, JTextField namesInput, JButton submitBtn, List<String> names, JFrame frame) {
        String[] fortunesBulgarianArr = {
                "На теб се пада Път далечен, да скиташ много си обречен. Но тук, в родната ти къща, една любов ще те завръща.",
                "Пада ти се ненадейно много щастие семейно!",
                "Ще работиш без дори да спираш, плановете си ще реализираш.",
                "Стига ти тая награда, парата на тебе се пада!",
                "Голям късмет избра си, браво – ще се радваш на отлично здраве!",
                "И тази година добър късмет улучи – пада ти се здраве и благополучие!",
                "Ще изпъкнеш с качества пред другите и се издигаш – твои са заслугите.",
                "През тази година ти смело пристъпяй напред, защото късмет ще те дебне навред!",
                "Таз година е щастлива, сила и здраве ще преливат!",
                "Стягай багажа за воаяжа!",
                "Държавна лотария, тото, на твоя страна е числото!",
                "Щастие, обич и разбирателство ще получиш с едно ново приятелство!",
                "Забавления те чакат, радост и щастливи дни! Таз година на купона кралят ще си точно ти!",
                "На тебе Мързелът се пада – да мързелуваш ти приляга! Съвсем ще му отпуснеш края, но късмета ще намериш, зная!",
                "Бяла спретната къщурка с две коли отпред и мадама за притурка – туй е твоят нов късмет!",
                "А за теб ще е Веселие, това ще е нормално ежедневие. Ще се редят купон подир купон, от теб се чака да им даваш тон.",
                "На теб Учение се пада, май това ти най-приляга. Не измисляй как да кръшкаш, имаш още изпити да тръшкаш!"
        };

        //Create a modifiable list, so you can remove fortunes to avoid people getting the same one
        List<String> fortunesBulgarian = Arrays
                .stream(fortunesBulgarianArr)
                .collect(Collectors.toList());


        //Get a random fortune for each person
        Random random = new Random();
        StringBuilder resultsMessage = new StringBuilder();
        for (String name : names) {
            int randomFortuneIndex = random.nextInt(0, fortunesBulgarian.size());
            String fortune = fortunesBulgarian.get(randomFortuneIndex);
            //remove it from the list to avoid people getting the same fortune
            fortunesBulgarian.remove(randomFortuneIndex);
            resultsMessage.append(String.format("<h3>На %s се падна:</h3> <p>%s</p><hr>", name, fortune));
        }

        JLabel resultsOutput = new JLabel("<html>"
                + resultsMessage
                + "</html>");

        resultsOutput.setBounds(50, 0, 300, 700);
        resultsOutput.setVisible(false);
        frame.add(resultsOutput);

        //Hide input form after valid input (not blank) has been submitted
        namesLabel.setVisible(false);
        namesInput.setVisible(false);
        submitBtn.setVisible(false);

        //Visualize results label
        frame.setSize(700, 700);
        //frame.pack(); //Adjust frame size to fit its contents; NOT the solution, no scroll, starts very small, you have to stretch it a lot with larger inputs
        resultsOutput.setVisible(true);
    }


}
