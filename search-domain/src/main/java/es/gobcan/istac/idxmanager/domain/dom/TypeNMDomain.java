package es.gobcan.istac.idxmanager.domain.dom;

public enum TypeNMDomain {
    
    //**********************
    // RECURSOS ESTADISTICOS
    //**********************
    
    DATASET_DS("DS", "dominio.nucleo.tipo.ds", true),               //  DATASET Dataset DS
    DATASET_DSM("DSM", "dominio.nucleo.tipo.dsm", true),            //  DATASET > matriz de datos   Dataset DSM
    DATASET_DSC("DSC", "dominio.nucleo.tipo.dsc", true),            //  DATASET > cubo  Dataset DSC
    DATASET_DST("DST", "dominio.nucleo.tipo.dst", true),            //  DATASET > tabla Dataset DST
    
    FIGURA_F("F", "dominio.nucleo.tipo.f", true),                   //  FIGURA  Image   F
    FIGURA_FG("FG", "dominio.nucleo.tipo.fg", true),                //  FIGURA > grafico    Image   FG
    FIGURA_FM("FM", "dominio.nucleo.tipo.fm", true),                //  FIGURA > mapa   Image   FM
    
    COLLECTION_P("P", "dominio.nucleo.tipo.p", true),               //  COLECCION DE DATOS Y FIGURAS    Colecction  P
    COLLECTION_PDD("PDD", "dominio.nucleo.tipo.pdd", true),         //  COLECCION > datos detallados    Colecction  PDD
    COLLECTION_PDC("PDC", "dominio.nucleo.tipo.pdc", true),         //  COLECCION > datos comentados    Colecction  PDC
    COLLECTION_PIE("PIE", "dominio.nucleo.tipo.pie", true),         //  COLECCION > informes estadisticos   Colecction  PIE
    COLLECTION_PEB("PEB", "dominio.nucleo.tipo.peb", true),         //  COLECCION > estadisticas de bolsillo    Colecction  PEB

    NOTICIAS_N("N", "dominio.nucleo.tipo.n", true),                 //  NOTICIAS    Collecction N
    NOTICIAS_NE("NE", "dominio.nucleo.tipo.ne", true),              //  NOTICIAS > estadistica  Collecction NE
    
    //*************************
    // RECURSOS COMPLEMENTARIOS
    //*************************
    
    //Valor para englobar todos los complementarios, este valor nos lo dara la pesta�a
    COMPLEMENTARIA("COMP","dominio.nucleo.tipo.comp",false),
    
    COMPLEMENTARIA_WEB("COMP_WEB","dominio.nucleo.tipo.comp",false), //Los recursos de info complementaria encontrados en la web tendran este tipo
    
    METODOLOGIA_M("M", "dominio.nucleo.tipo.m", false),             // METODOLOGIA  Text    M
    METODOLOGIA_MM("MM", "dominio.nucleo.tipo.mm", false),          // METODOLOGIA > metodologia    Text    MM
    METODOLOGIA_MRM("MRM", "dominio.nucleo.tipo.mrm", false),       // METODOLOGIA > recomendacion metodologica Text    MRM
    METODOLOGIA_MNM("MNM", "dominio.nucleo.tipo.mnm", false),       // METODOLOGIA > normativa metodologica Text    MNM
    METODOLOGIA_MCTM("MCTM", "dominio.nucleo.tipo.mctm", false),    // METODOLOGIA > cuaderno de trabajo metodologico   Text    MCTM
    METODOLOGIA_MIC("MIC", "dominio.nucleo.tipo.mic", false),       // METODOLOGIA > informe de calidad Text    MIC
    METODOLOGIA_MC("MC", "dominio.nucleo.tipo.mc", false),          // METODOLOGIA > cuestionario   Text    MC
    METODOLOGIA_MCD("MCD", "dominio.nucleo.tipo.mcd", false),       // METODOLOGIA > conceptos y definiciones   Dataset, Text   MCD
    
    VOCABULARIO_V("V", "dominio.nucleo.tipo.v", false),             // VOCABULARIO  Dataset, Text   V
    VOCABULARIO_VCLC("VCLC", "dominio.nucleo.tipo.vclc", false),    // VOCABULARIO > clasificacion o lista de codigos   Dataset, Text   VCLC
    VOCABULARIO_VCGT("VCGT", "dominio.nucleo.tipo.vcgt", false),    // VOCABULARIO > glosario o tesauro Dataset, Text   VCGT
    
    INVESTIGACION_I("I", "dominio.nucleo.tipo.i", false),           // INVESTIGACION    Text    I
    INVESTIGACION_III("III", "dominio.nucleo.tipo.iii", false),     // INVESTIGACION > informe de investigacion Text    III
    INVESTIGACION_IAI("IAI", "dominio.nucleo.tipo.iai", false),     // INVESTIGACION > articulo de investigacion    Text    IAI

    // OTROS (CUSTOM)
    // TODO: Mirar a ver que hacemos
    RECURSO_INFORMATIVO_RI("RI", "dominio.nucleo.tipo.ri", true);
    
    
    private String siglas;
    private String descripcion;
    private boolean isRecursoEstaditico;
    
    
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getSiglas() {
        return siglas;
    }
    
    public boolean isRecursoEstaditico() {
        return isRecursoEstaditico;
    }
    
    TypeNMDomain(String siglasIN, String descripcionIN, boolean isRecursoEstadisticoIN) {
        this.siglas = siglasIN;
        this.descripcion = descripcionIN;
        this.isRecursoEstaditico = isRecursoEstadisticoIN;
    }


}
