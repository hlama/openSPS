package com.purchasing.health.services;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.purchasing.health.models.*;
import com.purchasing.health.repositories.AccountBankRepository;
import com.purchasing.health.repositories.OrgunitRepository;
import com.purchasing.health.repositories.SettingAmountRepository;
import com.purchasing.health.repositories.ZoneSanteRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class PdfExporter {
    @Autowired
    private OrgunitRepository orgunitTypeRepository;
   @Autowired
    private ZoneSanteRepository zoneSanteRepository;
   @Autowired
    private AccountBankRepository accountBankRepository;
   private SettingAmountRepository settingAmountRepository;
    private List<Map<String,Object>> dataToPdf;

    public List<Map<String, Object>> getDataToPdf() {
        return dataToPdf;
    }

    public void setDataToPdf(List<Map<String, Object>> dataToPdf) {
        this.dataToPdf = dataToPdf;
    }

    private  Double totalPoint=0.0;
    private  Double score=0.0;
    private  Double sumToHave=0.0;
    private  Double amount=0.0;

    private ZoneSante zoneSante;
    private AccountBank accountBank;
    private String monthPaid;
    private Integer year;
    private TypeOrgunit typeOrgunit;
    private SettingAmount settingAmount;
    private Orgunits orgunits;
    private Double amountCalculed;

    public Double getAmountCalculed() {
        return amountCalculed;
    }

    public void setAmountCalculed(Double amountCalculed) {
        this.amountCalculed = amountCalculed;
    }

    public Orgunits getOrgunits() {
        return orgunits;
    }

    public void setOrgunits(Orgunits orgunits) {
        this.orgunits = orgunits;
    }

    public SettingAmount getSettingAmount() {
        return settingAmount;
    }

    public void setSettingAmount(SettingAmount settingAmount) {
        this.settingAmount = settingAmount;
    }

    public TypeOrgunit getTypeOrgunit() {
        return typeOrgunit;
    }

    public void setTypeOrgunit(TypeOrgunit typeOrgunit) {
        this.typeOrgunit = typeOrgunit;
    }

    private static final String[]months={"Janvier","Fevrier","Mars","Avril",
            "Mai","Juin","Juillet","Aout","Septembre","Octobre","Novembre","Decembre"};


    public ZoneSante getZoneSante() {
        return zoneSante;
    }

    public void setZoneSante(ZoneSante zoneSante) {
        this.zoneSante = zoneSante;
    }

    public AccountBank getAccountBank() {
        return accountBank;
    }

    public String getMonthPaid() {
        return monthPaid;
    }

    public void setMonthPaid(String monthPaid) {
        this.monthPaid = monthPaid;
    }

    public void setAccountBank(AccountBank accountBank) {
        this.accountBank = accountBank;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }


    public PdfExporter(List<Map<String, Object>> dataToPdf) {
        this.dataToPdf = dataToPdf;

    }
    public PdfExporter(){}
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.WHITE);
        cell.setPadding(5);
        cell.setHorizontalAlignment(1);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("N°", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Domaines", font));
        table.addCell(cell);


        cell.setPhrase(new Phrase("Points disponibles", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Points qualité attribues (Données entrées dans DHIS2 AS)", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Score Obtenu", font));
        table.addCell(cell);
    }
    private void writeTableData(PdfPTable table) {
        for (Map<String,Object> serviceHealth : this.dataToPdf) {
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setColor(Color.BLACK);
            PdfPCell cellID = new PdfPCell();
            PdfPCell cellDomaine = new PdfPCell();
            PdfPCell cellPoint = new PdfPCell();
            PdfPCell cellValue = new PdfPCell();
            PdfPCell cellScore = new PdfPCell();

            cellID.setBackgroundColor(Color.WHITE);
            cellID.setPadding(5);
            cellID.setHorizontalAlignment(1);
            cellID.setPhrase(new Phrase(serviceHealth.get("id").toString(), font));

            cellDomaine.setBackgroundColor(Color.WHITE);
            cellDomaine.setPadding(5);
            cellDomaine.setHorizontalAlignment(1);
            cellDomaine.setPhrase(new Phrase(serviceHealth.get("dataelementname").toString(), font));

            cellPoint.setBackgroundColor(Color.WHITE);
            cellPoint.setPadding(5);
            cellPoint.setHorizontalAlignment(1);
            cellPoint.setPhrase(new Phrase(serviceHealth.get("point").toString(), font));

            cellValue.setBackgroundColor(Color.WHITE);
            cellValue.setPadding(5);
            cellValue.setHorizontalAlignment(1);
            cellValue.setPhrase(new Phrase(serviceHealth.get("value").toString(), font));

            cellScore.setBackgroundColor(Color.WHITE);
            cellScore.setPadding(5);
            cellScore.setHorizontalAlignment(1);
            int scoreEachService=(int) ((Double.parseDouble(serviceHealth.get("value").toString())
                    /Double.parseDouble(serviceHealth.get("point").toString()))*100.0d);
            cellScore.setPhrase(new Phrase(
                    Double.toString(scoreEachService)+"%", font));

            table.addCell(cellID);
            table.addCell(cellDomaine);
            table.addCell(cellPoint);
            table.addCell(cellValue);
            table.addCell(cellScore);
            totalPoint+=Double.parseDouble(serviceHealth.get("point").toString());
            sumToHave+=Double.parseDouble(serviceHealth.get("value").toString());
        }
        System.out.printf("%s points%n", totalPoint);
        System.out.printf("%s Point recus%n", sumToHave);
        Font fontFooter = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontFooter.setColor(Color.BLACK);
        score= (sumToHave /totalPoint) *100;
        amount=(Double)(sumToHave /totalPoint)*this.getSettingAmount().getAmount();
       // amount=this.amountCalculed;
        PdfPCell cellTotalLabel = new PdfPCell();
        cellTotalLabel.setPhrase(new Phrase("TOTAL GENERAL",fontFooter));
        cellTotalLabel.setHorizontalAlignment(1);
        cellTotalLabel.setColspan(2);
        PdfPCell cellTotalPoint = new PdfPCell();
        cellTotalPoint.setPhrase(new Phrase(totalPoint.toString(),fontFooter));
        cellTotalPoint.setHorizontalAlignment(1);
        PdfPCell cellsumToHave = new PdfPCell();
        cellsumToHave.setPhrase(new Phrase(sumToHave.toString(),fontFooter));
        cellsumToHave.setHorizontalAlignment(1);
        PdfPCell cellScore = new PdfPCell();
        cellScore.setPhrase(new Phrase(score.toString()+"%",fontFooter));
        cellScore.setHorizontalAlignment(1);
        PdfPCell amountLabel=new PdfPCell();
        PdfPCell cellSubdisde = new PdfPCell();
        PdfPCell cellSubdisdevalue = new PdfPCell();

        table.addCell(cellTotalLabel);
        table.addCell(cellTotalPoint);
        table.addCell(cellsumToHave);
        table.addCell(cellScore);

        cellSubdisde.setPhrase(new Phrase(this.getSettingAmount().getLabel(),fontFooter));
        cellSubdisde.setHorizontalAlignment(1);
        cellSubdisdevalue.setPhrase(new Phrase(String.format("Dollars Americain ($) %s", amount),fontFooter));
        cellSubdisde.setColspan(2);
        cellSubdisdevalue.setColspan(3);
        cellSubdisdevalue.setHorizontalAlignment(2);
        table.addCell(cellSubdisde);
        //table.addCell(cellSubdisde1);
        table.addCell(cellSubdisdevalue);

    }
    public void export(HttpServletResponse response, Provinces province, ZoneSante zs) throws DocumentException, IOException {
      //  System.out.println(String.format("Amount Label:%s",this.getSettingAmount().getLabel()));
        Map<String,Object>singleData=dataToPdf.get(0);
        PdfPTable tableInfo=new PdfPTable(2);
        Font fontFooter = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontFooter.setColor(Color.BLACK);
        PdfPCell cellProvinceLabel = new PdfPCell();
        PdfPCell cellZoneSanteLabel = new PdfPCell();
        PdfPCell cellProvince = new PdfPCell();
        PdfPCell cellZoneSante = new PdfPCell();
        PdfPCell cellBankLabel = new PdfPCell();
        PdfPCell cellBank = new PdfPCell();
        PdfPCell cellAccountBankLabel = new PdfPCell();
        PdfPCell cellAccountBank = new PdfPCell();
        PdfPCell cellPeriodeLabel = new PdfPCell();
        PdfPCell cellPeriode = new PdfPCell();
        PdfPCell celldategenerateLabel = new PdfPCell();
        PdfPCell celldategenerate = new PdfPCell();

        cellProvinceLabel.setPhrase(new Phrase("Province",fontFooter));
       // cellProvinceLabel.setBorder(0);
        cellProvinceLabel.setPadding(10.0f);
        cellProvinceLabel.setBorder(0);
        cellProvince.setPhrase(new Phrase(province.getName().substring(2),fontFooter));
       // cellProvince.setBorder(0);
        cellProvince.setPadding(10.0f);
        cellProvince.setBorder(0);

        cellZoneSanteLabel.setPhrase(new Phrase("Structure",fontFooter));
        cellZoneSanteLabel.setPadding(10.0f);
        cellZoneSanteLabel.setBorder(0);
      //  cellZoneSanteLabel.setBorder(0);
        cellZoneSante.setPhrase(new Phrase(this.getZoneSante().getName(),fontFooter));
        cellZoneSante.setPadding(10.0f);
        cellZoneSante.setBorder(0);
      //  cellZoneSante.setBorder(0);

        cellBankLabel.setPhrase(new Phrase("Banque, agence",fontFooter));
        cellBankLabel.setPadding(10.0f);
        cellBankLabel.setBorder(0);
        //  cellZoneSanteLabel.setBorder(0);
        cellBank.setPhrase(new Phrase(this.accountBank.getIdbank().getName(),fontFooter));
        cellBank.setPadding(10.0f);
        cellBank.setBorder(0);

        cellAccountBankLabel.setPhrase(new Phrase("Numéro de compte ",fontFooter));
        cellAccountBankLabel.setPadding(10.0f);
        cellAccountBankLabel.setBorder(0);
        //  cellZoneSanteLabel.setBorder(0);
        cellAccountBank.setPhrase(new Phrase(this.accountBank.getAccountnumber(),fontFooter));
        cellAccountBank.setPadding(10.0f);
        cellAccountBank.setBorder(0);

        cellPeriodeLabel.setPhrase(new Phrase("Période",fontFooter));
        cellPeriodeLabel.setPadding(10.0f);
        cellPeriodeLabel.setBorder(0);
        int monthIndex=Integer.parseInt(this.getMonthPaid());
        String beginPeriode=months[(monthIndex-1)-2];
        String lastPeriode=months[monthIndex-1];
        cellPeriode.setPhrase(new Phrase(
                String.format(
                        "%s - %s %s",beginPeriode,lastPeriode, year
                )
                ,fontFooter));
        cellPeriode.setPadding(10.0f);
        cellPeriode.setBorder(0);
        celldategenerateLabel.setPhrase(new Phrase("Généré le",fontFooter));
        celldategenerateLabel.setPadding(10.0f);
        celldategenerateLabel.setBorder(0);
        celldategenerate.setPhrase(new Phrase(
                String.format("%s/%s/%s",
                        LocalDate.now().getDayOfMonth(),
                        LocalDate.now().getMonthValue(),
                        LocalDate.now().getYear()
                ),fontFooter
        ));
        celldategenerate.setPadding(10.0f);
        celldategenerate.setBorder(0);
        tableInfo.addCell(cellProvinceLabel);
        tableInfo.addCell(cellProvince);
        tableInfo.addCell(cellZoneSanteLabel);
        tableInfo.addCell(cellZoneSante);
        tableInfo.addCell(cellPeriodeLabel);
        tableInfo.addCell(cellPeriode);
        tableInfo.addCell(celldategenerateLabel);
        tableInfo.addCell(celldategenerate);
        tableInfo.addCell(cellBankLabel);
        tableInfo.addCell(cellBank);
        tableInfo.addCell(cellAccountBankLabel);
        tableInfo.addCell(cellAccountBank);
        PdfPTable  tableHeaderImage=new PdfPTable(3);
       // tableHeaderImage.getDefaultCell().setFixedHeight(100.0f);
        tableHeaderImage.getDefaultCell().setBorder(0);
        PdfPCell imgGouv=new PdfPCell();
        PdfPCell imgCordaid=new PdfPCell();
        PdfPCell imgS3G=new PdfPCell();
        Image imagLogoGouv=Image.getInstance(getClass().getResource("/static/img/logo_gouv.jpg"));
        Image imgCord=Image.getInstance(getClass().getResource("/static/images/cordaid-logo.png"));
        Image imgProject=Image.getInstance(getClass().getResource("/static/img/logo_S3G.jpg"));
        imgGouv.setImage(imagLogoGouv);
        imgGouv.setFixedHeight(100.0f);
        imgCordaid.setImage(imgCord);
        imgCordaid.setFixedHeight(100.0f);
        imgS3G.setImage(imgProject);
        imgS3G.setFixedHeight(100.0f);
        imgGouv.setPaddingLeft(50.0f);
        imgGouv.setBorder(0);
        imgS3G.setBorder(0);
        imgCordaid.setBorder(0);
        tableHeaderImage.addCell(imgGouv);
        tableHeaderImage.addCell(imgCordaid);
        tableHeaderImage.addCell(imgS3G);
        //tableHeaderImage.setWidths(new float[] { 3.5f, 3.0f, 3.0f});
        tableHeaderImage.setWidthPercentage(100);
        PdfPTable tableAmount=new PdfPTable(2);
        PdfPCell cellAmountLabel=new PdfPCell();
        PdfPCell cellAmount=new PdfPCell();
        cellAmountLabel.setPhrase(new Phrase("MONTANT A PAYER",fontFooter));
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("FACTURE TRIMESTRIELLE DEFINITIVE ECZ", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 4.5f, 2.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        PdfPTable tableSign = new PdfPTable(5);
        PdfPCell cellSign1=new PdfPCell();
        PdfPCell cellSignSpace=new PdfPCell();
        PdfPCell cellSign2=new PdfPCell();
        cellSign1.setPhrase(new Phrase("Representant CORDAID",fontFooter));
        cellSign1.setBorder(0);
        cellSign1.setColspan(2);
        cellSign2.setPhrase(new Phrase("Representant GT Finacement",fontFooter));
        cellSign2.setHorizontalAlignment(2);
        cellSign2.setColspan(2);
        cellSign2.setBorder(0);
        cellSignSpace.setPhrase(new Phrase("        "));
        cellSignSpace.setBorder(0);
        cellSign1.setPaddingTop(30);
        cellSign2.setPaddingTop(30);
        cellSignSpace.setPaddingTop(30);
        tableSign.addCell(cellSign1);
        tableSign.addCell(cellSignSpace);
        tableSign.addCell(cellSign2);
        tableSign.setWidthPercentage(100f);

        writeTableHeader(table);
        writeTableData(table);
        document.add(tableHeaderImage);
        document.add(tableInfo);
        document.add(table);
        document.add(tableSign);

        document.close();

    }

    public void exportOrg(HttpServletResponse response, Provinces province,Orgunits org) throws DocumentException, IOException {
        //  System.out.println(String.format("Amount Label:%s",this.getSettingAmount().getLabel()));
        Map<String,Object>singleData=dataToPdf.get(0);
        PdfPTable tableInfo=new PdfPTable(2);
        Font fontFooter = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontFooter.setColor(Color.BLACK);
        PdfPCell cellProvinceLabel = new PdfPCell();
        PdfPCell cellZoneSanteLabel = new PdfPCell();
        PdfPCell cellProvince = new PdfPCell();
        PdfPCell cellZoneSante = new PdfPCell();
        PdfPCell cellBankLabel = new PdfPCell();
        PdfPCell cellBank = new PdfPCell();
        PdfPCell cellAccountBankLabel = new PdfPCell();
        PdfPCell cellAccountBank = new PdfPCell();
        PdfPCell cellPeriodeLabel = new PdfPCell();
        PdfPCell cellPeriode = new PdfPCell();
        PdfPCell celldategenerateLabel = new PdfPCell();
        PdfPCell celldategenerate = new PdfPCell();

        cellProvinceLabel.setPhrase(new Phrase("Province",fontFooter));
        // cellProvinceLabel.setBorder(0);
        cellProvinceLabel.setPadding(10.0f);
        cellProvinceLabel.setBorder(0);
        cellProvince.setPhrase(new Phrase(province.getName().substring(2),fontFooter));
        // cellProvince.setBorder(0);
        cellProvince.setPadding(10.0f);
        cellProvince.setBorder(0);

        cellZoneSanteLabel.setPhrase(new Phrase("Structure",fontFooter));
        cellZoneSanteLabel.setPadding(10.0f);
        cellZoneSanteLabel.setBorder(0);
        //  cellZoneSanteLabel.setBorder(0);
        cellZoneSante.setPhrase(new Phrase(org.getName(),fontFooter));
        cellZoneSante.setPadding(10.0f);
        cellZoneSante.setBorder(0);
        //  cellZoneSante.setBorder(0);

        cellBankLabel.setPhrase(new Phrase("Banque, agence",fontFooter));
        cellBankLabel.setPadding(10.0f);
        cellBankLabel.setBorder(0);
        //  cellZoneSanteLabel.setBorder(0);
        cellBank.setPhrase(new Phrase(this.accountBank.getIdbank().getName(),fontFooter));
        cellBank.setPadding(10.0f);
        cellBank.setBorder(0);

        cellAccountBankLabel.setPhrase(new Phrase("Numéro de compte ",fontFooter));
        cellAccountBankLabel.setPadding(10.0f);
        cellAccountBankLabel.setBorder(0);
        //  cellZoneSanteLabel.setBorder(0);
        cellAccountBank.setPhrase(new Phrase(this.accountBank.getAccountnumber(),fontFooter));
        cellAccountBank.setPadding(10.0f);
        cellAccountBank.setBorder(0);

        cellPeriodeLabel.setPhrase(new Phrase("Période",fontFooter));
        cellPeriodeLabel.setPadding(10.0f);
        cellPeriodeLabel.setBorder(0);
        int monthIndex=Integer.parseInt(this.getMonthPaid());
        String beginPeriode=months[(monthIndex-1)-2];
        String lastPeriode=months[monthIndex-1];
        cellPeriode.setPhrase(new Phrase(
                String.format(
                        "%s - %s %s",beginPeriode,lastPeriode, year
                )
                ,fontFooter));
        cellPeriode.setPadding(10.0f);
        cellPeriode.setBorder(0);
        celldategenerateLabel.setPhrase(new Phrase("Généré le",fontFooter));
        celldategenerateLabel.setPadding(10.0f);
        celldategenerateLabel.setBorder(0);
        celldategenerate.setPhrase(new Phrase(
                String.format("%s/%s/%s",
                        LocalDate.now().getDayOfMonth(),
                        LocalDate.now().getMonthValue(),
                        LocalDate.now().getYear()
                ),fontFooter
        ));
        celldategenerate.setPadding(10.0f);
        celldategenerate.setBorder(0);
        tableInfo.addCell(cellProvinceLabel);
        tableInfo.addCell(cellProvince);
        tableInfo.addCell(cellZoneSanteLabel);
        tableInfo.addCell(cellZoneSante);
        tableInfo.addCell(cellPeriodeLabel);
        tableInfo.addCell(cellPeriode);
        tableInfo.addCell(celldategenerateLabel);
        tableInfo.addCell(celldategenerate);
        tableInfo.addCell(cellBankLabel);
        tableInfo.addCell(cellBank);
        tableInfo.addCell(cellAccountBankLabel);
        tableInfo.addCell(cellAccountBank);
        PdfPTable  tableHeaderImage=new PdfPTable(3);
        // tableHeaderImage.getDefaultCell().setFixedHeight(100.0f);
        tableHeaderImage.getDefaultCell().setBorder(0);
        PdfPCell imgGouv=new PdfPCell();
        PdfPCell imgCordaid=new PdfPCell();
        PdfPCell imgS3G=new PdfPCell();
        Image imagLogoGouv=Image.getInstance(getClass().getResource("/static/img/logo_gouv.jpg"));
        Image imgCord=Image.getInstance(getClass().getResource("/static/images/cordaid-logo.png"));
        Image imgProject=Image.getInstance(getClass().getResource("/static/img/logo_S3G.jpg"));
        imgGouv.setImage(imagLogoGouv);
        imgGouv.setFixedHeight(100.0f);
        imgCordaid.setImage(imgCord);
        imgCordaid.setFixedHeight(100.0f);
        imgS3G.setImage(imgProject);
        imgS3G.setFixedHeight(100.0f);
        imgGouv.setPaddingLeft(50.0f);
        imgGouv.setBorder(0);
        imgS3G.setBorder(0);
        imgCordaid.setBorder(0);
        tableHeaderImage.addCell(imgGouv);
        tableHeaderImage.addCell(imgCordaid);
        tableHeaderImage.addCell(imgS3G);
        //tableHeaderImage.setWidths(new float[] { 3.5f, 3.0f, 3.0f});
        tableHeaderImage.setWidthPercentage(100);
        PdfPTable tableAmount=new PdfPTable(2);
        PdfPCell cellAmountLabel=new PdfPCell();
        PdfPCell cellAmount=new PdfPCell();
        cellAmountLabel.setPhrase(new Phrase("MONTANT A PAYER",fontFooter));
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("FACTURE TRIMESTRIELLE DEFINITIVE", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 4.5f, 2.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        PdfPTable tableSign = new PdfPTable(5);
        PdfPCell cellSign1=new PdfPCell();
        PdfPCell cellSignSpace=new PdfPCell();
        PdfPCell cellSign2=new PdfPCell();
        cellSign1.setPhrase(new Phrase("Representant CORDAID",fontFooter));
        cellSign1.setBorder(0);
        cellSign1.setColspan(2);
        cellSign2.setPhrase(new Phrase("Representant GT Finacement",fontFooter));
        cellSign2.setHorizontalAlignment(2);
        cellSign2.setColspan(2);
        cellSign2.setBorder(0);
        cellSignSpace.setPhrase(new Phrase("        "));
        cellSignSpace.setBorder(0);
        cellSign1.setPaddingTop(30);
        cellSign2.setPaddingTop(30);
        cellSignSpace.setPaddingTop(30);
        tableSign.addCell(cellSign1);
        tableSign.addCell(cellSignSpace);
        tableSign.addCell(cellSign2);
        tableSign.setWidthPercentage(100f);

        writeTableHeader(table);
        writeTableData(table);
        document.add(tableHeaderImage);
        document.add(tableInfo);
        document.add(table);
        document.add(tableSign);

        document.close();

    }
}
