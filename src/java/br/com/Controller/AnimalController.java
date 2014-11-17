package br.com.Controller;

import br.com.Bean.AnimalBean;
import br.com.DAO.AnimalDAO;
import br.com.Utilitarios.Utilidades;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name="animalController")
@ApplicationScoped
//@SessionScoped
public class AnimalController implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private AnimalBean animal;
    private DataModel listaAnimaisGeral;
    private DataModel listaAnimaisParaDoacao;
    private boolean exibeBtnSalvar;
    private boolean exibeBtnAlterar;
    private boolean exibeImagem;
    private UploadedFile file;
    
    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public boolean isExibeBtnSalvar() {
        return exibeBtnSalvar;
    }

    public boolean isExibeImagem() {
        return exibeImagem;
    }

    public void setExibeImagem(boolean exibeImagem) {
        this.exibeImagem = exibeImagem;
    }

    public void setExibeBtnSalvar(boolean exibeBtnSalvar) {
        this.exibeBtnSalvar = exibeBtnSalvar;
    }

    public boolean isExibeBtnAlterar() {
        return exibeBtnAlterar;
    }

    public void setExibeBtnAlterar(boolean exibeBtnAlterar) {
        this.exibeBtnAlterar = exibeBtnAlterar;
    }

    public AnimalController() {
        animal = new AnimalBean();
        this.exibeImagem = Boolean.FALSE;

    }

    public AnimalBean getAnimal() {
        return animal;
    }

    public void setAnimal(AnimalBean animal) {
        this.animal = animal;
    }

    public DataModel getListaAnimaisGeral() {
        try {
            AnimalDAO animalDAO = new AnimalDAO();
            
          //  animalDAO.listarAnimalGeral();
            listaAnimaisGeral = new ListDataModel(animalDAO.listarAnimalGeral());
            return listaAnimaisGeral;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAnimaisGeral;
    }

    public void setListaAnimaisGeral(DataModel listaAnimaisGeral) {
        this.listaAnimaisGeral = listaAnimaisGeral;
    }
    

    public DataModel getListaAnimaisParaDoacao() {
        try {
            System.out.println("passou get");
            AnimalDAO animalDAO = new AnimalDAO();
            
            animalDAO.listarAnimalParaDoacao();
            listaAnimaisParaDoacao = new ListDataModel(animalDAO.listarAnimalParaDoacao());
            return listaAnimaisParaDoacao;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAnimaisParaDoacao;
    }

    public void setListaAnimaisParaDoacao(DataModel listaAnimaisParaDoacao) {
        this.listaAnimaisParaDoacao = listaAnimaisParaDoacao;
    }
    
    public void novoAnimal(){
        animal = new AnimalBean();
        escondeBntAlterar();
        
    }
    
    public String salvaAnimal(int idCliente){
        try {
            AnimalDAO animalDAO = new AnimalDAO();
            
            animal.setIdCliente(idCliente);
            
            if(animalDAO.salvarAnimal(animal)){
                FacesContext contexto = FacesContext.getCurrentInstance();
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "cadastro com sucesso!", ""));
                return "/meusAnimais?faces-redirect=true";
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "erro";
        
    }

    public String alteraAnimal(){
        try {
            AnimalDAO animalDAO = new AnimalDAO();
            
            if(animalDAO.alterarAnimal(animal)){
                FacesContext contexto = FacesContext.getCurrentInstance();
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "alterado com sucesso!", ""));
                return "/meusAnimais?faces-redirect=true";
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "erro";
        
    }
    
    public void deletaAnimal(){
        try {
            AnimalDAO animalDAO = new AnimalDAO();
            
            if(animalDAO.excluirAnimal(animal)){
              msgDelecaoAnimal();
              getListaAnimaisParaDoacao();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    public void autorizarAnimal(){
        try {
            AnimalDAO animalDAO = new AnimalDAO();
            FacesContext contexto = FacesContext.getCurrentInstance();
            
            if (animal == null ){
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Um registro precisa ser selecionado!", ""));
                
            }else{ 
              if(animalDAO.autorizaAnimal(animal)){
                  contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Autorizado com sucesso!", ""));
              }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void selecionarAnimalGeral(){
        animal = (AnimalBean) listaAnimaisGeral.getRowData();
    }

    public void selecionarAnimalGeralParaDoacao(){
        getListaAnimaisGeral();
        animal = (AnimalBean) listaAnimaisParaDoacao.getRowData();
    }

    public void msgDelecaoAnimal() {
        addMessage("Atenção", "Animal deletado com sucesso.");
    }
     
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void escondeBntAlterar(){
       // animal = null;
        setExibeBtnAlterar(false);
        setExibeBtnSalvar(true);
    }
    
    public void escondeBtnSalvar(){
        setExibeBtnAlterar(true);
        setExibeBtnSalvar(false);
    }

    public void sinalizarAdocao(){
        try {
            AnimalDAO animalDAO = new AnimalDAO();
            FacesContext contexto = FacesContext.getCurrentInstance();
            
            if (animal == null ){
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Um animal precisa ser selecionado!", ""));
                
            }else{ 
              if(animalDAO.sinalizaAdotado(animal)){
                  contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Status Atualizado com sucesso!", ""));
              }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    public void handleFileUpload(FileUploadEvent event) throws IOException {

       UploadedFile file = event.getFile();
       byte[] foto = IOUtils.toByteArray(file.getInputstream());
       animal.setFotoPrincipal(foto);

   }    

    public  DefaultStreamedContent byteToImagem(byte[] imgBytes) {
//        if(!Arrays.toString(imgBytes).isEmpty()){
//        ByteArrayInputStream img = new ByteArrayInputStream(imgBytes);
//        return new DefaultStreamedContent(img,"image/jpg");
//        }else{
//            return null;
//        }
        ByteArrayInputStream img = new ByteArrayInputStream(imgBytes);
        return new DefaultStreamedContent(img,"image/jpg");
        
    }    

    public void exibeImagem(){
        this.setExibeImagem(Boolean.TRUE);
    }
    
    public void carregaFotos() {
 
        try {
            System.out.println(animal.getDescricao());
            System.out.println(Arrays.toString(animal.getFotoPrincipal()));
//            if (!Arrays.toString(animal.getFotoPrincipal()).isEmpty()){

                ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                AnimalDAO animalDAO = new AnimalDAO();


                File folder = new File(sContext.getRealPath("/temp"));
                if (!folder.exists())
                    folder.mkdirs();


                for (AnimalBean a : animalDAO.listarAnimalParaDoacao()) {

                    String nomeArquivo = a.getId() + ".jpg";
                    String arquivo = sContext.getRealPath("/temp") + File.separator
                            + nomeArquivo;

                    criaArquivo(a.getFotoPrincipal(), arquivo);
                }
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
 
    }
   private void criaArquivo(byte[] bytes, String arquivo) {
        FileOutputStream fos;
 
        try {
            fos = new FileOutputStream(arquivo);
            fos.write(bytes);
 
            fos.flush();
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }    

//public StreamedContent getDynamicImage() {
// 
// String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("image_id");
// AnimalDAO animalDAO = new AnimalDAO();
// 
// if (id != null && animalDAO.listarAnimalGeral() != null && !animalDAO.listarAnimalGeral().isEmpty()) {
// 
//     byte imagemId = Byte.parseByte(id);
//     
//     for (AnimalBean a : animalDAO.listarAnimalParaDoacao()) {
//         if (a.getFotoPrincipal() == imagemId) {
//             return a.getFotoPrincipal();
//         }
//     }
// 
// }
 
// return new DefaultStreamedContent();
// }
}
