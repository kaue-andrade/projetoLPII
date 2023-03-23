package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DTO.Livro;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    private GerenciadorBD bd;

    public LivroDAO() {
        bd = new GerenciadorBD();
    }

    public void cadastrar(Livro livro) throws SQLException {
        try (Connection conexao = bd.conectar(); 
             PreparedStatement comando = conexao.prepareStatement(
             "INSERT INTO livro (titulo, ano, paginas, autores, preco) VALUES (?, ?, ?, ?, ?)")
            ) 
        {
            comando.setString(1, livro.getTitulo());
            comando.setInt(2, livro.getAno());
            comando.setInt(3, livro.getPaginas());
            comando.setString(4, livro.getAutores());
            comando.setDouble(5, livro.getPreco());
            
            comando.executeUpdate();
        }
    }

    public List<Livro> listar() throws SQLException {

        List<Livro> listaDeLivros = new ArrayList<>();

        try (Connection conexao = bd.conectar(); 
             PreparedStatement comando = conexao.prepareStatement(
             "SELECT ID, titulo, ano, paginas, autores, preco FROM livro");
             ResultSet tabela = comando.executeQuery()
            )
        {
            while (tabela.next()) {
                Livro livro = new Livro();

                livro.setId(tabela.getInt("id"));
                livro.setTitulo(tabela.getString("titulo"));
                livro.setAno(tabela.getInt("ano"));
                livro.setPaginas(tabela.getInt("paginas"));
                livro.setAutores(tabela.getString("autores"));
                livro.setPreco(tabela.getDouble("preco"));

                listaDeLivros.add(livro);
            }
        }

        return listaDeLivros;
    }
    
    public void excluir(Livro livro) throws SQLException {
        try (Connection conexao = bd.conectar();
             PreparedStatement comando = conexao.prepareStatement (
                     "DELETE FROM livro WHERE id = ?")
        )
            
        {    
        comando.setInt(1, livro.getId());
        
        comando.executeUpdate();
        }
    }
    
    public void alterar(Livro livro) throws SQLException {
        try(Connection conexao = bd.conectar();
           PreparedStatement comando = conexao.prepareStatement(
           "UPDATE livro SET titulo = ?, ano = ?, paginas = ?, autores = ?, preco = ? WHERE id = ?")
           )   
                
           {
               comando.setString(1, livro.getTitulo());
               comando.setInt(2, livro.getAno());
               comando.setInt(3, livro.getPaginas());
               comando.setString(4, livro.getAutores());
               comando.setDouble(5, livro.getPreco());
               comando.setInt(6, livro.getId());
               
               comando.executeUpdate();
               
               
           }
    
    }
}
