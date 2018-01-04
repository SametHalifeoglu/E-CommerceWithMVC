package com.sirketadi.calisma;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import UrunProperty.UrunBilgileriProperty;
import Utils.DB;

@Controller
public class AnasayfaController {
	
	DB db=new DB();
	
	//------------------Kullanc�y� ve admini kar��layan panel------------------------------------------------
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String anasayfa( Model model) {
		return "anasayfa";
	}
	
	
	//------------------Kullan�c�n�n g�rmek istedi�i kategori sayfalar�n� temsil ediyor------------------------------
	

	@RequestMapping(value = "/urunleri/{kategori}", method = RequestMethod.GET)
	public String urunleredallan(@PathVariable String kategori, Model model) {
		ArrayList<UrunBilgileriProperty> resimler=new ArrayList<UrunBilgileriProperty>();
		String urundetayi="";
		try {
			String query="select *from urun_bilgileri where urun_kategori='"+kategori+"'";
			ResultSet rs=db.baglan().executeQuery(query);
			while(rs.next()) {
				UrunBilgileriProperty urunbilgileri=new UrunBilgileriProperty();
				urunbilgileri.setUrun_id(rs.getString("urun_id"));
				urunbilgileri.setUrun_resim(rs.getString("urun_resim"));
				urundetayi=rs.getString("urun_kisacik").substring(0, 25)+"...";
				urunbilgileri.setUrun_detay(urundetayi);
				urunbilgileri.setUrun_fiyat(rs.getString("urun_fiyat"));
				urunbilgileri.setUrun_kategori(rs.getString("urun_kategori"));
				urunbilgileri.setUrun_editor(rs.getString("urun_editor"));
				urunbilgileri.setUrun_adi(rs.getString("urun_adi"));
				resimler.add(urunbilgileri);
			}
			model.addAttribute("resimler", resimler);
			model.addAttribute("baslik", kategori);
			
		} catch (Exception e) {
			System.err.println("�r�n resimlerini getirme hatas�:" + e.getMessage());
		}
		
		
		
		return "user/urunlersayfasi";
	}
	
	
	
	
	
	

	
}
