package tech.talent.language;

public class Turkish implements Language{

    @Override
    public String getLockMessage() {
        return  "  Paketinin ödemesini yapmadığınız için kara listesiniz";
    }

    @Override
    public String getExpiredMessage() {
       return "  Paketinin süresi dolduğu için message gönderilemedi.";
    }
}
