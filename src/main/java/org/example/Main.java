package org.example;

import org.example.dao.*;
import org.example.model.*;

import java.time.LocalDate;
import java.util.*;

public class Main {
    static Scanner ler = new Scanner(System.in);

    public static void main(String[] args) {
        inicio();
    }

    public static void inicio() {
        boolean sair = false;
        System.out.println("----- SISTEMA DE CONTROLE DE PRODUÇÃO INDUSTRIAL -----\n" +
                "1 - Cadastrar setor\n" +
                "2 - Cadastrar Máquina\n" +
                "3 - Cadastrar Produto\n" +
                "4 - Cadastrar Matéria-Prima\n" +
                "5 - Criar Ordem de Produção\n" +
                "6 - Associar Matérias-Primas à Ordem\n" +
                "7 - Executar Produção\n" +
                "8 - Consultar Ordens e Estoque\n" +
                "0 - Sair");

        int opcao = ler.nextInt();
        ler.nextLine();

        switch (opcao) {
            case 1: {
                cadastrarSetor();
                break;
            }
            case 2: {
                cadastrarMaquina();
                break;
            }
            case 3: {
                cadastrarProduto();
                break;
            }
            case 4: {
                cadastrarMateriaPrima();
                break;
            }
            case 5: {
                criarOrdemProducao();
                break;
            }
            case 6: {
                associarMateriaPrimaOrdem();
                break;
            }
            case 7: {
                executarProducao();
                break;
            }
        }
        if (!sair) {
            inicio();
        }
    }

    public static void cadastrarSetor() {
        System.out.println("Insira o nome do setor que gostaria de cadastrar: ");
        String nomeSetor = ler.nextLine();

        if (!nomeSetor.isEmpty()) {
            var setor = new Setor(nomeSetor);
            var setorDao = new setorDAO();

            boolean setorExiste = setorDao.buscarSetorPorNome(setor);

            if (!setorExiste) {
                setorDao.inserirSetor(setor);
            } else {
                System.out.println("Setor já cadastrado no sistema!");
            }
        } else {
            System.out.println("Dads inválidos! Preencha corretamete e tente novamente.");
            cadastrarSetor();
        }
    }

    public static void cadastrarMaquina() {
        System.out.println("Digite o nome da máquina que gostaria de cadastrar: ");
        String nomeMaquina = ler.nextLine();

        if (!nomeMaquina.isEmpty()) {
            List<Integer> opcoesSetores = new ArrayList<>();
            var setorDao = new setorDAO();

            List<Setor> setores = setorDao.listarSetores();

            for (Setor setor : setores) {
                System.out.println("----- SETOR -----\n" +
                        "ID: " + setor.getId() + "\n" +
                        "Nome: " + setor.getNome() + "\n" +
                        "-----------------------------------");

                opcoesSetores.add(setor.getId());
            }
            System.out.println("Digite o ID do setor que gostaria de cadastrar a máquina: ");
            int idSetor = ler.nextInt();
            ler.nextLine();

            if (opcoesSetores.contains(idSetor)) {
                var maquina = new Maquina(nomeMaquina, idSetor, "OPERACIONAL");
                var maquinaDao = new maquinaDAO();

                boolean maquinaNoSetorExiste = maquinaDao.buscarMaquinaPorNome(maquina);

                if (!maquinaNoSetorExiste) {
                    maquinaDao.inserirMaquina(maquina);
                } else {
                    System.out.println("Máquina já cadastrada no setor!");
                }
            } else {
                System.out.println("Dite o ID de um setor válido!");
            }
        } else {
            System.out.println("Nome da máquina vazio. Insira valores válidos.");
        }
    }

    public static void cadastrarProduto() {
        System.out.println("Digite o nome do produto que gostaria de cadastrar: ");
        String nomeProduto = ler.nextLine();

        System.out.println("Digite a categoria que esse produto pertence: ");
        String nomeCategoria = ler.nextLine();

        if (!nomeProduto.isEmpty() && !nomeCategoria.isEmpty()) {
            var produto = new Produto(nomeProduto, nomeCategoria);
            var produtoDao = new produtoDAO();

            boolean produtoExiste = produtoDao.buscarProduto(produto);

            if (!produtoExiste) {
                produtoDao.inserirProduto(produto);
            } else {
                System.out.println("Produto já cadastrado!");
            }
        } else {
            System.out.println("Nome do produto ou nome da categoria vazios. Insira valores válidos!");
        }
    }

    public static void cadastrarMateriaPrima() {
        System.out.println("Digite o nome da matéria-prima que gostaria de cadastrar: ");
        String nomeMateria = ler.nextLine();

        System.out.println("Digite a quantidade de matéria-prima em estoque: ");
        Double estoque = ler.nextDouble();
        ler.nextLine();

        if (!nomeMateria.isEmpty() && estoque >= 0) {
            var materiaPrima = new MateriaPrima(nomeMateria, estoque);
            var materiaPrimaDao = new materiaPrimaDAO();

            boolean materiaPrimaExiste = materiaPrimaDao.buscarMateriaPrima(materiaPrima);

            if (!materiaPrimaExiste) {
                materiaPrimaDao.inserirMateriaPrima(materiaPrima);
            } else {
                System.out.println("Matéria-Prima já cadastrada no sistema!");
            }
        } else {
            System.out.println("O nome da matéria está vazio ou estoque é menor que zero. Insira um nome válido e um estoque maior ou igual a 0!");
        }
    }

    public static void criarOrdemProducao() {
        var produtoDao = new produtoDAO();
        List<Integer> opcoesIdProdutos = new ArrayList<>();
        List<Produto> produtos = produtoDao.listarProdutos();

        for (Produto produto : produtos) {
            System.out.println("------ PRODUTO -----\n" +
                    "ID: " + produto.getId() + "\n" +
                    "Nome: " + produto.getNome() + "\n" +
                    "Categoria: " + produto.getCategoria() + "\n" +
                    "----------------------------------------------");

            opcoesIdProdutos.add(produto.getId());
        }

        System.out.println("Digite o ID do produto que gostaria de selecionar: ");
        int idProduto = ler.nextInt();
        ler.nextLine();

        if (opcoesIdProdutos.contains(idProduto)) {
            var maquinaDao = new maquinaDAO();
            List<Integer> opcoesIdMaquinas = new ArrayList<>();
            List<Maquina> maquinas = maquinaDao.listarMaquinas();

            for (Maquina maquina : maquinas) {
                System.out.println("----- MÁQUINA -----\n" +
                        "ID: " + maquina.getId() + "\n" +
                        "Nome: " + maquina.getNome() + "\n" +
                        "ID setor: " + maquina.getIdSetor() + "\n" +
                        "Status: " + maquina.getStatus() + "\n" +
                        "-------------------------------------------");

                opcoesIdMaquinas.add(maquina.getId());
            }

            System.out.println("Digite o ID da máquina:");
            int idMaquina = ler.nextInt();
            ler.nextLine();

            if (opcoesIdMaquinas.contains(idMaquina)) {
                System.out.println("Informe a quantidade a ser produzida: ");
                Double quantidadeProd = ler.nextDouble();
                ler.nextLine();

                if (quantidadeProd <= 0) {
                    System.out.println("Insira uma quantidade válida!");
                } else {
                    var ordemProducaoDao = new ordemProducaoDAO();
                    var ordemProducao = new OrdemProducao(idProduto, idMaquina, quantidadeProd, LocalDate.now(), "PENDENTE");
                    ordemProducaoDao.inserirOrdemProducao(ordemProducao);
                    maquinaDao.atualizaStatus(idMaquina, "EM_PRODUCAO");
                }
            } else {
                System.out.println("ID da máquina não encontrado! Digite um ID válido.");
                criarOrdemProducao();
            }

        } else {
            System.out.println("ID do produto não encontrada! Digite um ID válido.");
            criarOrdemProducao();
        }
    }

    public static void associarMateriaPrimaOrdem() {
        var ordemProducaoDao = new ordemProducaoDAO();
        List<Integer> opcoesIdOrdemProducao = new ArrayList<>();
        List<OrdemProducao> ordens = ordemProducaoDao.listarOrdensDeProducao();

        for (OrdemProducao ordemProducao : ordens) {
            System.out.println("----- ORDEM DE PRODUÇÃO -----\n" +
                    "ID: " + ordemProducao.getId() + "\n" +
                    "ID produto: " + ordemProducao.getIdProduto() + "\n" +
                    "ID máquina: " + ordemProducao.getIdMaquina() + "\n" +
                    "Quantidade a produzir: " + ordemProducao.getQuantidadeProduzir() + "\n" +
                    "Data da solicitação: " + ordemProducao.getDataSolicitacao() + "\n" +
                    "Status: " + ordemProducao.getStatus() + "\n" +
                    "--------------------------------------------------------------------");

            opcoesIdOrdemProducao.add(ordemProducao.getId());
        }

        System.out.println("Digite o ID da ordem de produção:");
        int idOrdemProducao = ler.nextInt();
        ler.nextLine();

        if (opcoesIdOrdemProducao.contains(idOrdemProducao)) {
            var materiaProducaoDao = new materiaPrimaDAO();
            List<Integer> opcoesIdMateriaPrima = new ArrayList<>();
            List<MateriaPrima> materiaPrimas = materiaProducaoDao.listarMateriasPrimas();

            for (MateriaPrima materiaPrima : materiaPrimas) {
                System.out.println("----- MATÉRIA PRIMA ----- \n" +
                        "ID: " + materiaPrima.getId() + "\n" +
                        "Nome: " + materiaPrima.getNome() + "\n" +
                        "Estoque: " + materiaPrima.getEstoque() + "\n" +
                        "--------------------------------------------------");

                opcoesIdMateriaPrima.add(materiaPrima.getId());
            }

            System.out.println("Digite o ID da matéria prima:");
            int idMateriaPrima = ler.nextInt();
            ler.nextLine();

            if (opcoesIdMateriaPrima.contains(idMateriaPrima)) {
                System.out.println("Digite a quantidade de matéria-prima a ser utilizada: ");
                double quantidadeMP = ler.nextDouble();
                ler.nextLine();

                double quantidadeEstoque = 0;
                for (MateriaPrima materiaPrima : materiaPrimas) {
                    if (idMateriaPrima == materiaPrima.getId()) {
                        quantidadeEstoque = materiaPrima.getEstoque();
                    }
                }

                if (quantidadeEstoque >= quantidadeMP) {
                    var ordemMateriaPrimaDao = new ordemMateriaPrimaDAO();
                    var ordemMateriaPrima = new OrdemMateriaPrima(idOrdemProducao, idMateriaPrima, quantidadeMP);
                    ordemMateriaPrimaDao.inserirOrdemMateriaPrima(ordemMateriaPrima);
                } else {
                    System.out.println("O valor colocado é maior que o disponível em estoque! Insira um valor válido.");
                    associarMateriaPrimaOrdem();
                }
            } else {
                System.out.println("A opção selecionada não está disponível. Tente novamente: ");
                associarMateriaPrimaOrdem();
            }
        } else {
            System.out.println("A opção selecionada não está disponível. Tente novamente: ");
            associarMateriaPrimaOrdem();
        }
    }

    public static void executarProducao() {
        var ordemProducaoDao = new ordemProducaoDAO();
        List<Integer> opcoesIdOrdemProducao = new ArrayList<>();
        List<OrdemProducao> ordens = ordemProducaoDao.listarOrdensDeProducao();

        for (OrdemProducao ordemProducao : ordens) {
            System.out.println("----- ORDEM DE PRODUÇÃO -----\n" +
                    "ID: " + ordemProducao.getId() + "\n" +
                    "ID produto: " + ordemProducao.getIdProduto() + "\n" +
                    "ID máquina: " + ordemProducao.getIdMaquina() + "\n" +
                    "Quantidade a produzir: " + ordemProducao.getQuantidadeProduzir() + "\n" +
                    "Data da solicitação: " + ordemProducao.getDataSolicitacao() + "\n" +
                    "Status: " + ordemProducao.getStatus() + "\n" +
                    "--------------------------------------------------------------------");

            opcoesIdOrdemProducao.add(ordemProducao.getId());
        }

        System.out.println("Digite o ID da ordem que gostaria de selecionar: ");
        int idOrdem = ler.nextInt();
        ler.nextLine();

        Map<Integer, Double> atualizacoes = new HashMap<>();

        var materiaPrimaDao = new materiaPrimaDAO();
        var ordemMateriaPrimaDao = new ordemMateriaPrimaDAO();

        if (opcoesIdOrdemProducao.contains(idOrdem)) {
            List<OrdemMateriaPrima> ordensDeMateriaPrima = ordemMateriaPrimaDao.buscarOrdemMateriaPrimaPorIdOrdemProducao(idOrdem);

            for (OrdemMateriaPrima ordemMateriaPrima : ordensDeMateriaPrima) {
                double estoque = materiaPrimaDao.buscarEstoquePorID(ordemMateriaPrima.getIdMateriaPrima());
                if (estoque >= ordemMateriaPrima.getQuantidade()) {
                    atualizacoes.put(ordemMateriaPrima.getIdMateriaPrima(), (estoque - ordemMateriaPrima.getQuantidade()));
                } else {
                    System.out.println("Estoque de matéria-prima insuficiente para executar produção!");
                }
            }

            for (Map.Entry<Integer, Double> entrada : atualizacoes.entrySet()) {
                materiaPrimaDao.atualizaEstoque(entrada.getKey(), entrada.getValue());
            }

            ordemProducaoDao.atualizaStatus(idOrdem);

            int idMaquina = 0;
            for (OrdemProducao ordemProducao : ordens) {
                if (ordemProducao.getId() == idOrdem) {
                    idMaquina = ordemProducao.getIdMaquina();
                }
            }

            var maquinaDao = new maquinaDAO();
            maquinaDao.atualizaStatus(idMaquina, "OPERACIONAL");
        } else {
            System.out.println("ID da Ordem de Produção inválido! Tente novamente: ");
            executarProducao();
        }
    }
}