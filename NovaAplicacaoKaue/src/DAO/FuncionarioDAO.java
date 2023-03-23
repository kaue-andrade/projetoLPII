package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DTO.Funcionario;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private GerenciadorBD bd;

    public FuncionarioDAO() {
        bd = new GerenciadorBD();
    }

    public void cadastrar(Funcionario funcionario) throws SQLException {
        try (Connection conexao = bd.conectar(); 
             PreparedStatement comando = conexao.prepareStatement(
             "INSERT INTO funcionario (nome, email, telefone) VALUES (?, ?, ?)")
            ) 
        {
            comando.setString(1, funcionario.getNome());
            comando.setString(2, funcionario.getEmail());
            comando.setDouble(3, funcionario.getSalario());
            comando.setInt(4, funcionario.getAno());
            
            comando.executeUpdate();
        }
    }

    public List<Funcionario> listar() throws SQLException {

        List<Funcionario> listaDeFuncionarios = new ArrayList<>();

        try (Connection conexao = bd.conectar(); 
             PreparedStatement comando = conexao.prepareStatement(
             "SELECT ID, nome, email, telefone FROM funcionario");
             ResultSet tabela = comando.executeQuery()
            )
        {
            while (tabela.next()) {
                Funcionario funcionario = new Funcionario();

                funcionario.setId(tabela.getInt("id"));
                funcionario.setNome(tabela.getString("nome"));
                funcionario.setEmail(tabela.getString("email"));
                funcionario.setSalario(tabela.getDouble("salario"));
                funcionario.setAno(tabela.getInt("ano"));

                listaDeFuncionarios.add(funcionario);
            }
        }

        return listaDeFuncionarios;
    }
}
