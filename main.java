import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Data de Nascimento: " + dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}

class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return super.toString() + ", Salário: R$ " + decimalFormat.format(salario) + ", Função: " + funcao;
    }
}

public class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(1985, 10, 15), new BigDecimal("2500.00"), "Gerente"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1978, 12, 20), new BigDecimal("2000.00"), "Analista"));
        funcionarios.add(new Funcionario("José", LocalDate.of(1990, 5, 3), new BigDecimal("1800.00"), "Assistente"));

        // Remover o funcionário "João" da lista
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // Imprimir todos os funcionários
        System.out.println("Funcionários:");
        funcionarios.forEach(System.out::println);

        // Aumento de salário de 10%
        funcionarios.forEach(funcionario -> funcionario.setSalario(funcionario.getSalario().multiply(new BigDecimal("1.10"))));

        // Agrupar funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Imprimir os funcionários agrupados por função
        System.out.println("\nFuncionários Agrupados por Função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(System.out::println);
        });

        // Imprimir os funcionários que fazem aniversário em outubro (mês 10) e dezembro (mês 12)
        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        funcionarios.stream()
                .filter(funcionario ->
                        funcionario.getDataNascimento().getMonthValue() == 10 || funcionario.getDataNascimento().getMonthValue() == 12)
                .forEach(System.out::println);

        // Imprimir o funcionário com maior idade
        System.out.println("\nFuncionário com Maior Idade:");
        Funcionario maisVelho = Collections.max(funcionarios, Comparator.comparing(funcionario ->
                funcionario.getDataNascimento().until(LocalDate.now()).getYears()));
        System.out.println("Nome: " + maisVelho.getNome() +
                ", Idade: " + maisVelho.getDataNascimento().until(LocalDate.now()).getYears());

        // Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\nFuncionários em Ordem Alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(System.out::println);

        // Imprimir o total dos salários dos funcionários
        System.out.println("\nTotal dos Salários dos Funcionários:");
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("R$ " + decimalFormat.format(totalSalarios));

        // Imprimir quantos salários mínimos ganha cada funcionário (considerando salário mínimo de R$ 1212.00)
        System.out.println("\nSalários Mínimos:");
        funcionarios.forEach(funcionario -> {
            int salariosMinimos = funcionario.getSalario().divide(new BigDecimal("1212.00"), 0, BigDecimal.ROUND_DOWN).intValue();
            System.out.println(funcionario.getNome() + ": " + salariosMinimos + " salário(s) mínimo(s)");
        });
    }
}
