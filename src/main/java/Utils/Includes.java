package Utils;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sirketadi.calisma.GirisyapKayitolController;

import UrunProperty.KategoriProperty;
import UrunProperty.MusteriBilgilerProperty;
import UrunProperty.UrunProperty;

@Controller
public class Includes {
	
	

	// css dosyalar�n�n oldu�u b�l�m
	@RequestMapping(value = "/css", method = RequestMethod.GET)
	public String cssCagir() {
		return "inc/css";
	}
	// ---------------------------------------------------------------------------------------------------------

	// js dosyalar�n�n oldu�u b�l�m
	@RequestMapping(value = "/js", method = RequestMethod.GET)
	public String jsCagir() {
		return "inc/js";
	}
	// ---------------------------------------------------------------------------------------------------------

	// header dosyalar�n�n oldu�u b�l�m
	@RequestMapping(value = "headeruser", method = RequestMethod.GET)
	public String headerCagir(Model model) {
		
		if(GirisyapKayitolController.musteribilgisi.size()==0) {
			model.addAttribute("musterigirisiyok", 0);
		}else {
			MusteriBilgilerProperty mp=GirisyapKayitolController.musteribilgisi.get(0);
			model.addAttribute("musterigirisivar", mp.getUser_adi());
		}
		
		return "inc/headeruser";
	}
	// ---------------------------------------------------------------------------------------------------------

	DB db = new DB();

	// menu dosyalar�n�n oldu�u b�l�m
	@RequestMapping(value = "menu", method = RequestMethod.GET)
	public String menuCagir(Model model) {

		ArrayList<KategoriProperty> ls = new ArrayList<KategoriProperty>();
		ArrayList<String> kategori = new ArrayList<String>();

		String adminbilgi = null;
		try {
			String query = "select *from admin_panel";
			ResultSet rs = db.baglan().executeQuery(query);
			while (rs.next()) {

				adminbilgi = rs.getString("admin_bilgi");
			}

			model.addAttribute("adminadsoyad", adminbilgi);

		} catch (Exception e) {
			System.err.println("Admin bilgilerini getirme hatas�: " + e.getMessage());
		}

		// ---------------------------------------------------------------------------------------------------------

		try {
			String que = "select *from kategori";
			ResultSet rs = db.baglan().executeQuery(que);
			while (rs.next()) {
				KategoriProperty kp = new KategoriProperty();
				kp.setKategid(rs.getString("kategid"));
				kp.setKategadi(rs.getString("kategadi"));
				kategori.add(rs.getString("kategadi"));
				ls.add(kp);
			}
			model.addAttribute("ls", ls);
		} catch (Exception e) {
			System.err.println("kategori getirme hatas�:" + e.getMessage());
		}

		ArrayList<UrunProperty> urunProp = new ArrayList<UrunProperty>();
		System.err.println("�al��t�m");
		try {
			String que = "select urunid,urunadi,urunkategori from urunler";
			ResultSet rs = db.baglan().executeQuery(que);
			while (rs.next()) {
				UrunProperty urun = new UrunProperty();
				urun.setUrunid(rs.getString("urunid"));
				urun.setUrunadi(rs.getString("urunadi"));
				urun.setUrunkategori(rs.getString("urunkategori"));
				urunProp.add(urun);
			}

		} catch (Exception e) {
			System.err.println("kategori getirme hatas�:" + e.getMessage());
		}

		model.addAttribute("kats", urunProp);

		System.out.println("urun sayisi : " + urunProp.size());
		System.out.println("kategori sayisi " + ls.size());

		return "inc/menu";
	}
	// ---------------------------------------------------------------------------------------------------------

	// footer dosyalar�n�n oldu�u b�l�m
	@RequestMapping(value = "footer", method = RequestMethod.GET)
	public String footerCagir() {
		return "inc/footer";
	}
	// ---------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "UserMenu", method = RequestMethod.GET)
	public String UserMenuCagir(Model model) {

		ArrayList<KategoriProperty> ls = new ArrayList<KategoriProperty>();
		
		ArrayList<String> kategori = new ArrayList<String>();

		try {
			String que = "select *from kategori";
			ResultSet rs = db.baglan().executeQuery(que);
			while (rs.next()) {
				KategoriProperty kp = new KategoriProperty();
				kp.setKategid(rs.getString("kategid"));
				kp.setKategadi(rs.getString("kategadi"));
				kategori.add(rs.getString("kategadi"));
				ls.add(kp);
			}
			model.addAttribute("UserKategori", ls);
		} catch (Exception e) {
			System.err.println("kategori getirme hatas�:" + e.getMessage());
		}
		
		return "inc/UserMenu";
	}
	// ---------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "HeaderAdmin", method = RequestMethod.GET)
	public String HeaderAdmin(Model model) {

		String adminbilgi = null;
		try {
			String query = "select *from admin_panel";
			ResultSet rs = db.baglan().executeQuery(query);
			while (rs.next()) {

				adminbilgi = rs.getString("admin_bilgi");
			}

			model.addAttribute("adminadsoyad", adminbilgi);

		} catch (Exception e) {
			System.err.println("Admin bilgilerini getirme hatas�: " + e.getMessage());
		}
		return "inc/HeaderAdmin";

	}
}
