import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

class UserExtension {

    private static final File userData = new File(System.getProperty("user.home") + "\\Stella\\User.json");
    private static final Scanner scanner = new Scanner(System.in);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static User createUserData() {
        User user = new User();
        System.out.println("Oi, eu sou a Stella, estou aqui para te ajudar a gastar menos energia, \n" +
                "assim poupando seu bolso e ajudando a salvar o planeta :)");
        setUserTariffs(user);
        setUserName(user);
        setUserAge(user);
        saveUserData(user);
        System.out.println("Salvando... \n" +
                "Iniciando...\n");
        return user;
    }

    public static void editUserData(User user) {
        do {
            System.out.print("\nO que deseja mudar?\n" +
                    "╔══════════════════════════════════════════════╗\n" +
                    "║[1] Nome                                      ║\n" +
                    "║[2] Idade                                     ║\n" +
                    "╠═════════ Opções avançadas, cuidado! ═════════╣\n" +
                    "║[3] Tarifa Base                               ║\n" +
                    "║[4] Valor da COSIP                            ║\n" +
                    "║[5] Valor ta taxa ECE                         ║\n" +
                    "║[6] Valor das taxas PIS + COFINS              ║\n" +
                    "║[7] Voltar valores de tarifas ao padrão       ║\n" +
                    "║[8] Deletar todos os dispositivos adicionados ║\n" +
                    "╠══════════════════════════════════════════════╣\n" +
                    "║[0] Voltar...                                 ║\n" +
                    "╚══════════════════════════════════════════════╝\n");
            int index = StellaExtension.Ui.selectOption(8);
            switch (index) {
                case 1:
                    setUserName(user);
                    break;
                case 2:
                    setUserAge(user);
                    break;
                case 3:
                    setUserTariff(user);
                    break;
                case 4:
                    setUserCosip(user);
                    break;
                case 5:
                    setUserEce(user);
                    break;
                case 6:
                    setUserPisCofins(user);
                    break;
                case 7:
                    resetUserTariffs(user);
                    break;
                case 8:
                    deleteUserDevices(user);
                    break;
                case 0:
                    return;
            }
            System.out.println("Salvando...");
        } while (true);
    }

    public static void saveUserData(User user) {
        try {
            mapper.writeValue(userData, user);
        } catch (Exception e) {
            StellaExtension.Ui.Error.unableToCreateUserFile();
        }
    }

    public static User loadUserData() {
        try {
            return mapper.readValue(userData, User.class);
        } catch (IOException e) {
            StellaExtension.Ui.Error.unableToReadUserFile();
            return null;
        }
    }

    public static boolean createUserFile() {
        try {
            return userData.getParentFile().mkdirs()
                    ? userData.createNewFile()
                    : userData.getParentFile().mkdirs() && userData.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            StellaExtension.Ui.Error.unableToCreateUserFile();
            return false;
        }
    }

    public static boolean isUserFileEmpty() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(userData.getPath()));
        } catch (Exception e) {
            return true;
        }
        try {
            return br.readLine() == null && userData.length() == 0;
        } catch (IOException e) {
            return true;
        }
    }

    public static void getEnergyConsumption(User user) {
        if (user.getDevices().isEmpty() || (user.getDevices().stream().mapToDouble(Device::getEnergyConsumption).sum() == 0)) {
            StellaExtension.Ui.Error.emptyOrZeroedList();
            return;
        }

        double monthlyUsage = Math.ceil(user.getDevices().stream().mapToDouble(Device::getEnergyConsumption).sum() * 30);
        double yearlyBill = (getMonthlyCompleteBill(user, monthlyUsage, user.getGreenFlag()) * 4) +
                (getMonthlyCompleteBill(user, monthlyUsage, user.getYellowFlag()) * 3) +
                (getMonthlyCompleteBill(user, monthlyUsage, user.getRedFlag1()) * 3) +
                (getMonthlyCompleteBill(user, monthlyUsage, user.getRedFlag2()) * 2);

        double monthlyBill = getMonthlyCompleteBill(user, monthlyUsage, user.getGreenFlag());


        System.out.println(String.format("\nOkay deixa eu ver aqui....\n" +
                        "Seu consumo de energia total por mês é de: %s kWh\n" +
                        "Desse jeito, em um mês de bandeira Verde você vai gastar: R$ %s!\n" +
                        "\n" +
                        "E me baseando no comportamento das bandeiras nos ultimos anos...\n" +
                        "Posso dizer que em um ano inteiro você gastaria R$ %s!!!",
                StellaExtension.Ui.getDoubleString(monthlyUsage),
                StellaExtension.Ui.getDoubleString(monthlyBill),
                StellaExtension.Ui.getDoubleString(yearlyBill)));

    }

    public static void getConsumptionGuide(User user) {
        if (user.getDevices().isEmpty() || (user.getDevices().stream().mapToDouble(Device::getEnergyConsumption).sum() == 0)) {
            StellaExtension.Ui.Error.emptyOrZeroedList();
            return;
        }
        ArrayList<Device> deviceList = user.getDevices();
        deviceList.sort(Comparator.comparing(Device::getEnergyConsumption).reversed());

        Device firstDevice = deviceList.get(0);

        System.out.println("\nCerto, é o seguinte então...\n" +
                "Esses são os aparelhos que usam mais energia na sua casa:\n");
        String deviceString = String.format("1°: %s, que gasta %s kWh por dia, ou seja, custa pra você R$ %s por mês!\n",
                firstDevice.getName(),
                StellaExtension.Ui.getDoubleString(firstDevice.getEnergyConsumption()),
                StellaExtension.Ui.getDoubleString(getMonthlyBill(user, (firstDevice.getEnergyConsumption() * 30), user.getGreenFlag())));

        if (deviceList.size() > 1) {
            Device secondDevice = deviceList.get(1);
            deviceString += String.format("2°: %s, que gasta %s kWh por dia, ou seja, custa pra você R$ %s por mês!\n",
                    secondDevice.getName(),
                    StellaExtension.Ui.getDoubleString(secondDevice.getEnergyConsumption()),
                    StellaExtension.Ui.getDoubleString(getMonthlyBill(user, (secondDevice.getEnergyConsumption() * 30), user.getGreenFlag())));
        }

        if (deviceList.size() > 2) {
            Device thirdDevice = deviceList.get(2);
            deviceString += String.format("3°: %s, que gasta %s kWh por dia, ou seja, custa pra você R$ %s por mês!\n",
                    thirdDevice.getName(),
                    StellaExtension.Ui.getDoubleString(thirdDevice.getEnergyConsumption()),
                    StellaExtension.Ui.getDoubleString(getMonthlyBill(user, (thirdDevice.getEnergyConsumption() * 30), user.getGreenFlag())));
        }

        System.out.println(deviceString);

        System.out.println("Acho que a partir de agora fica meio óbvio né?\n\n" +
                "Pra diminuir o custo da sua conta de luz você pode começar por diminuindo o uso desses aparelhos,\n" +
                "como são esses que mais gastam, diminuindo principalmente esses ai de cima, ja vai trazer um grande resultado!!!\n\n" +
                "É normal esquecer as coisas ligadas quando você não tá usando, ou até mesmo quando não está no mesmo cômodo,\n" +
                "se você prestar mais atenção nas coisas ligadas em outros cantos da casa, ja pode ajudar!\n\n" +
                "É isso, diminuindo o tempo de uso das coisas que mais gastam, \n" +
                "e desligando as coisas que estão ligadas mas você não está usando na hora, \n" +
                "são na minha opinião os melhores jeitos de poupar energia!!! :)");
    }

    private static void setUserName(User user) {
        System.out.print(user.getName() == null ?
                "Pra começar eu quero de saber o seu nome: " :
                "\nQual vai ser o nome novo? : ");
        do {
            String name = scanner.nextLine().trim();
            if (StellaExtension.isValidNameFormat(name)) {
                user.setName(name);
                saveUserData(user);
                return;
            }
            StellaExtension.Ui.Error.invalidNameFormatError();
        } while (true);
    }

    private static void setUserAge(User user) {
        System.out.print(user.getAge() == 0
                ? "E agora a sua idade: "
                : "\nQual vai ser a idade nova? : ");
        do {
            String age = scanner.nextLine().trim();
            if (StellaExtension.isValidAgeFormat(age)) {
                user.setAge(Integer.parseInt(age));
                saveUserData(user);
                return;
            }
            StellaExtension.Ui.Error.invalidAgeFormatError();
        } while (true);
    }

    private static void resetUserTariffs(User user) {
        if (StellaExtension.Ui.getConsent()) {
            System.out.println("\nOkay :)");
            user.setDefaultTariffs();
            saveUserData(user);
        }
    }

    private static void setUserTariffs(User user) {
        user.setDefaultTariffs();
        saveUserData(user);
    }

    private static void deleteUserDevices(User user) {
        if (StellaExtension.Ui.getConsent()) {
            System.out.print("\nOkay :) \nProntinho...");
            user.deleteDevices();
            saveUserData(user);
        }
    }

    private static void setUserTariff(User user) {
        System.out.print("\nQual vai ser o novo valor da tarifa? : ");
        do {
            String tariff = scanner.nextLine();
            if (StellaExtension.isPositiveDouble(tariff)) {
                user.setTariff(Double.parseDouble(tariff));
                saveUserData(user);

                return;
            }
            StellaExtension.Ui.Error.invalidNumberFormatError();
        } while (true);
    }

    private static void setUserPisCofins(User user) {
        System.out.print("\nQual vai ser o novo valor da tarifa? : ");
        do {
            String tariff = scanner.nextLine();
            if (StellaExtension.isPositiveDouble(tariff)) {
                user.setPisCofins(Double.parseDouble(tariff));
                saveUserData(user);
                return;
            }
            StellaExtension.Ui.Error.invalidNumberFormatError();
        } while (true);
    }

    private static void setUserEce(User user) {
        System.out.print("\nQual vai ser o novo valor da tarifa? : ");
        do {
            String tariff = scanner.nextLine();
            if (StellaExtension.isPositiveDouble(tariff)) {
                user.setEce(Double.parseDouble(tariff));
                saveUserData(user);

                return;
            }
            StellaExtension.Ui.Error.invalidNumberFormatError();
        } while (true);
    }

    private static void setUserCosip(User user) {
        System.out.print("\nQual vai ser o novo valor da tarifa? : ");
        do {
            String tariff = scanner.nextLine();
            if (StellaExtension.isPositiveDouble(tariff)) {
                user.setCosip(Double.parseDouble(tariff));
                saveUserData(user);
                return;
            }
            StellaExtension.Ui.Error.invalidNumberFormatError();
        } while (true);
    }

    private static double getMonthlyCompleteBill(User user, double monthlyUsage, Map<String, Double> flag) {
        getMonthlyBill(user, monthlyUsage, flag);
        double monthlyBill = getMonthlyBill(user, monthlyUsage, flag);
        monthlyBill += user.getCosip();
        return monthlyBill;
    }

    private static double getMonthlyBill(User user, double monthlyUsage, Map<String, Double> flag) {
        double monthlyBill = 0;
        double icms = 0;

        if (monthlyUsage > 200) {
            double highCost = getEnergyCost(user, monthlyUsage - 199, flag, "high");
            monthlyBill += highCost;
            icms += applyTaxes(highCost, 25);
        }

        if (monthlyUsage > 90) {
            double midCost = getEnergyCost(user, 109, flag, "mid");
            monthlyBill += midCost;
            icms += applyTaxes(midCost, 12);
        }

        monthlyBill += getEnergyCost(user, monthlyUsage > 90 ? 90 : monthlyUsage, flag, "low");
        monthlyBill += icms;
        monthlyBill += monthlyUsage * user.getEce();
        monthlyBill += applyTaxes(monthlyBill, user.getPisCofins());
        return monthlyBill;
    }

    private static double applyTaxes(double base, double percentage) {
        return base * (percentage / 100);
    }

    private static double getEnergyCost(User user, double energyConsumption, Map<String, Double> flag, String tier) {
        double tariff = user.getTariff();
        double tierPercentage = flag.get(tier) / 100;
        return energyConsumption * (tariff + (tariff * tierPercentage));
    }
}