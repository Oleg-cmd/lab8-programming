package modules;

public abstract class CommandOutputDecorator extends CommandOutput {
    // Базовый объект CommandOutput, который должен быть декорирован
    protected CommandOutput output;

    // Конструктор, который принимает базовый объект CommandOutput
    public CommandOutputDecorator(CommandOutput output) {
        this.output = output;
    }

    // Метод, который должен быть переопределен в классах-декораторах
    @Override
    public abstract void append(String s);
}