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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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

@ManagedBean(name = "animalController")
@SessionScoped
//@SessionScoped
public class AnimalController implements Serializable {

    private static final long serialVersionUID = 1L;

    private AnimalBean animal;
    private DataModel listaAnimaisGeral;
    private DataModel listaAnimaisParaDoacao;
    private boolean exibeBtnSalvar;
    private boolean exibeBtnAlterar;
    private boolean exibeFotoPrincipal;
    private boolean exibeFoto1;
    private boolean exibeFoto2;
    private boolean exibeFoto3;
    private UploadedFile file;
    private List<String> images;

    public boolean filterByName(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        String carName = value.toString().toUpperCase();
        filterText = filterText.toUpperCase();

        if (carName.contains(filterText)) {
            return true;
        } else {
            return false;
        }
    }

    public void iniciaFotos(int id) {

//        images = new ArrayList<String>();
//        images.add(String.valueOf(animal.getId()));
//        images.add(animal.getId()+"_1");
//        images.add(animal.getId()+"_2");
//        images.add(animal.getId()+"_3");
//        f = new File(sContext.getRealPath("/temp")+"/"+id+"_princ.jpg");
//        if(f.exists()){
//            images.add("./temp"+"/"+id+"_princ.jpg");
//        }
//
//        f = new File(sContext.getRealPath("/temp")+"/"+id+"_1.jpg");
//        if(f.exists()){
//            images.add("./temp"+"/"+id+"_1.jpg");
//        }
//        
//        f = new File(sContext.getRealPath("/temp")+"/"+id+"_2.jpg");
//        if(f.exists()){
//            images.add("./temp"+"/"+id+"_2.jpg");        }
//        
//        f = new File(sContext.getRealPath("/temp")+"/"+id+"_3.jpg");
//        if(f.exists()){
//            images.add("./temp"+"/"+id+"_3.jpg");
//        }
        ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        File f;

        f = new File(sContext.getRealPath("/temp") + "/" + id + "_princ.jpg");
        exibeFotoPrincipal = f.exists();

        f = new File(sContext.getRealPath("/temp") + "/" + id + "_1.jpg");
        exibeFoto1 = f.exists();

        f = new File(sContext.getRealPath("/temp") + "/" + id + "_2.jpg");
        exibeFoto2 = f.exists();

        f = new File(sContext.getRealPath("/temp") + "/" + id + "_3.jpg");
        exibeFoto3 = f.exists();

    }

    public List<String> getImages() {
        return images;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isExibeBtnSalvar() {
        return exibeBtnSalvar;
    }

    public boolean isExibeFotoPrincipal() {
        return exibeFotoPrincipal;
    }

    public void setExibeFotoPrincipal(boolean exibeFotoPrincipal) {
        this.exibeFotoPrincipal = exibeFotoPrincipal;
    }

    public boolean isExibeFoto1() {
        return exibeFoto1;
    }

    public void setExibeFoto1(boolean exibeFoto1) {
        this.exibeFoto1 = exibeFoto1;
    }

    public boolean isExibeFoto2() {
        return exibeFoto2;
    }

    public void setExibeFoto2(boolean exibeFoto2) {
        this.exibeFoto2 = exibeFoto2;
    }

    public boolean isExibeFoto3() {
        return exibeFoto3;
    }

    public void setExibeFoto3(boolean exibeFoto3) {
        this.exibeFoto3 = exibeFoto3;
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
        this.exibeFotoPrincipal = Boolean.FALSE;
        this.exibeFoto1 = Boolean.FALSE;
        this.exibeFoto2 = Boolean.FALSE;
        this.exibeFoto3 = Boolean.FALSE;

    }

    public AnimalBean getAnimal() {
        if (animal == null) {
            animal = new AnimalBean();
        }
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

            AnimalDAO animalDAO = new AnimalDAO();

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

    public void novoAnimal() {
        animal = new AnimalBean();
        escondeBntAlterar();

    }

    public String salvaAnimal(int idCliente) {
        try {
            AnimalDAO animalDAO = new AnimalDAO();

            animal.setIdCliente(idCliente);

            if (animalDAO.salvarAnimal(animal)) {
                FacesContext contexto = FacesContext.getCurrentInstance();
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "cadastro com sucesso!", ""));
                return "/meusanimais?faces-redirect=true";
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "erro";

    }

    public String alteraAnimal() {
        try {
            AnimalDAO animalDAO = new AnimalDAO();

            if (animalDAO.alterarAnimal(animal)) {
                FacesContext contexto = FacesContext.getCurrentInstance();
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "alterado com sucesso!", ""));
                return "/meusanimais?faces-redirect=true";
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "erro";

    }

    public void deletaAnimal() {
        try {
            AnimalDAO animalDAO = new AnimalDAO();
            FacesContext contexto = FacesContext.getCurrentInstance();

            if (animal == null) {
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Um animal precisa ser selecionado!", ""));

            } else {

                if (animalDAO.excluirAnimal(animal)) {
                    msgDelecaoAnimal();
                    getListaAnimaisParaDoacao();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void autorizarAnimal() {
        try {
            AnimalDAO animalDAO = new AnimalDAO();
            FacesContext contexto = FacesContext.getCurrentInstance();

            if (animal == null) {
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Um registro precisa ser selecionado!", ""));

            } else {
                if (animalDAO.autorizaAnimal(animal)) {
                    contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Autorizado com sucesso!", ""));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void negarAnimal() {
        try {
            AnimalDAO animalDAO = new AnimalDAO();
            FacesContext contexto = FacesContext.getCurrentInstance();

            if (animal == null) {
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Um registro precisa ser selecionado!", ""));

            } else {
                if (animalDAO.negaAnimal(animal)) {
                    contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Animal negado com sucesso!", ""));
                    //IMPLEMENTAR FUNÇÃO PARA ENVIAR EMAIL PARA O DONO AO NEGAR A PUBLICAÇÃO
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void selecionarAnimalGeral() {
        animal = (AnimalBean) listaAnimaisGeral.getRowData();
    }

    public void selecionarAnimalGeralParaDoacao() {
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

    public void escondeBntAlterar() {
        // animal = null;
        setExibeBtnAlterar(false);
        setExibeBtnSalvar(true);
    }

    public void escondeBtnSalvar() {
        setExibeBtnAlterar(true);
        setExibeBtnSalvar(false);
    }

    public String chamaTelaAlterar() {

        AnimalDAO animalDAO = new AnimalDAO();
        FacesContext contexto = FacesContext.getCurrentInstance();

        if (animal == null) {
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Um animal precisa ser selecionado!", ""));
            return "";
        } else {
            escondeBtnSalvar();
            return "/cadanimais?faces-redirect=true";

        }

    }

    public void sinalizarAdocao() {
        try {
            AnimalDAO animalDAO = new AnimalDAO();
            FacesContext contexto = FacesContext.getCurrentInstance();

            if (animal == null) {
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Um animal precisa ser selecionado!", ""));

            } else {
                if (animalDAO.sinalizaAdotado(animal)) {
                    contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Status Atualizado com sucesso!", ""));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void uploadFotoPrincipal(FileUploadEvent event) throws IOException {

        UploadedFile file = event.getFile();
        byte[] foto = IOUtils.toByteArray(file.getInputstream());
        animal.setFotoPrincipal(foto);
        setExibeFotoPrincipal(Boolean.TRUE);
        carregaFotosNoCadastro();
    }

    public void uploadFoto1(FileUploadEvent event) throws IOException {

        UploadedFile file = event.getFile();
        byte[] foto = IOUtils.toByteArray(file.getInputstream());
        animal.setFoto1(foto);
        setExibeFoto1(Boolean.TRUE);
        carregaFotosNoCadastro();

    }

    public void uploadFoto2(FileUploadEvent event) throws IOException {

        UploadedFile file = event.getFile();
        byte[] foto = IOUtils.toByteArray(file.getInputstream());
        animal.setFoto2(foto);
        setExibeFoto2(Boolean.TRUE);
        carregaFotosNoCadastro();

    }

    public void uploadFoto3(FileUploadEvent event) throws IOException {

        UploadedFile file = event.getFile();
        byte[] foto = IOUtils.toByteArray(file.getInputstream());
        animal.setFoto3(foto);
        setExibeFoto3(Boolean.TRUE);
        carregaFotosNoCadastro();

    }

    public DefaultStreamedContent byteToImagem(byte[] imgBytes) {
//        if(!Arrays.toString(imgBytes).isEmpty()){
//        ByteArrayInputStream img = new ByteArrayInputStream(imgBytes);
//        return new DefaultStreamedContent(img,"image/jpg");
//        }else{
//            return null;
//        }
        ByteArrayInputStream img = new ByteArrayInputStream(imgBytes);
        return new DefaultStreamedContent(img, "image/jpg");

    }

    public void exibeImagem() {
        this.setExibeFotoPrincipal(Boolean.TRUE);
    }

    public void carregaFotos() {

        try {

//            if (!Arrays.toString(animal.getFotoPrincipal()).isEmpty()){
            ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            AnimalDAO animalDAO = new AnimalDAO();

            File folder = new File(sContext.getRealPath("/temp"));
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }

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

    public void carregaFotosNoCadastro() {

        try {

//            if (!Arrays.toString(animal.getFotoPrincipal()).isEmpty()){
            ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            AnimalDAO animalDAO = new AnimalDAO();
            File f;

            File folder = new File(sContext.getRealPath("/temp"));
//            if (folder.exists()) {
//                folder.delete();
//                folder.mkdirs();
//            } else {
//                folder.mkdirs();
//            }

            for (AnimalBean a : animalDAO.listarAnimalParaDoacao()) {

                if (!Arrays.toString(a.getFotoPrincipal()).isEmpty() && !Arrays.toString(a.getFotoPrincipal()).equals("null")) {

                    String nomeArquivoFotoPrincipal = a.getId() + "_princ" + ".jpg";
                    String arquivoFotoPrincipal = sContext.getRealPath("/temp") + File.separator
                            + nomeArquivoFotoPrincipal;

                    f = new File(arquivoFotoPrincipal);

                    if (!f.exists()) {
                        criaArquivo(a.getFotoPrincipal(), arquivoFotoPrincipal);
                    }
                }

                if (!Arrays.toString(a.getFoto1()).isEmpty() && !Arrays.toString(a.getFoto1()).equals("null")) {
                    String nomeArquivo = a.getId() + "_1" + ".jpg";
                    String arquivo = sContext.getRealPath("/temp") + File.separator
                            + nomeArquivo;
                    f = new File(arquivo);

                    if (!f.exists()) {
                        criaArquivo(a.getFoto1(), arquivo);
                    }
                }

                if (!Arrays.toString(a.getFoto2()).isEmpty() && !Arrays.toString(a.getFoto2()).equals("null")) {
                    String nomeArquivo = a.getId() + "_2" + ".jpg";
                    String arquivo = sContext.getRealPath("/temp") + File.separator
                            + nomeArquivo;
                    f = new File(arquivo);

                    if (!f.exists()) {
                        criaArquivo(a.getFoto2(), arquivo);
                    }
                }

                if (!Arrays.toString(a.getFoto3()).isEmpty() && !Arrays.toString(a.getFoto3()).equals("null")) {
                    String nomeArquivo = a.getId() + "_3" + ".jpg";
                    String arquivo = sContext.getRealPath("/temp") + File.separator
                            + nomeArquivo;
                    f = new File(arquivo);

                    if (!f.exists()) {
                        criaArquivo(a.getFoto3(), arquivo);
                    }
                }
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

    public String getPreviewImg(int id) {
        File arq;
        ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        arq = new File(sContext.getRealPath("/temp") + File.separator + id + "_princ.jpg");

        if (arq.exists()) {
            return "/temp/" + id + "_princ.jpg";
        } else {
            return "/imagens/sem-foto.jpg";
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
