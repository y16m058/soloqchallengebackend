package com.soloqchallenge.demo.rest;

public enum LigaEnum {
	
	  
	  CHALLENGER("CHALLENGER", 1),
	  GRANDMASTER("GRANDMASTER", 2),
	  MASTER("MASTER", 3),
	  DIAMANTE1("DIAMOND I", 4),
	  DIAMANTE2("DIAMOND II", 5),
	  DIAMANTE3("DIAMOND III", 6),
	  DIAMANTE4("DIAMOND IV", 7),
	  PLATINO1("PLATINUM I", 8),
	  PLATINO2("PLATINUM II", 9),
	  PLATINO3("PLATINUM III", 10),
	  PLATINO4("PLATINUM IV", 11),
	  ORO1("GOLD I", 12),
	  ORO2("GOLD II", 13),
	  ORO3("GOLD III", 14),
	  ORO4("GOLD IV", 15),
	  PLATA1("SILVER I", 16),
	  PLATA2("SILVER II", 17),
	  PLATA3("SILVER III", 18),
	  PLATA4("SILVER IV", 19),
	  BRONCE1("BRONZE I", 20),
	  BRONCE2("BRONZE II", 21),
	  BRONCE3("BRONZE III", 22),
	  BRONCE4("BRONZE IV", 23),
	  HIERRO1("IRON I", 24),
	  HIERRO2("IRON II", 25),
	  HIERRO3("IRON III", 26),
	  HIERRO4("IRON IV", 27),
	  UNRANKED("UNRANKED",28);
	
	  public String getCode() {
		return code;
	}

	public Integer getValue() {
		return value;
	}

	private final String code;
	  private final Integer value;
	  
	  private LigaEnum(String code, Integer value) {
		  this.code = code;
		  this.value = value;
	  }
	  
	  public static LigaEnum valueForCode(String id) {
		    for (LigaEnum plt : LigaEnum.values()) {
		      if (plt.getCode().equals(id)) {
		        return plt;
		      }
		    }
		    return null;
	 }
}




