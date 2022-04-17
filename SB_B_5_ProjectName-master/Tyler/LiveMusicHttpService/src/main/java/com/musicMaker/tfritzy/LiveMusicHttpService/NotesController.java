package com.musicMaker.tfritzy.LiveMusicHttpService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotesController {
	
	private Notes notes;
	
	@RequestMapping("/getNotes")
    public Notes getNotes() {
		if (notes == null) {
			notes = new Notes();
		}

        return notes;
    }
	
	@RequestMapping("/setC")
	public void setC(@RequestParam(value="c", defaultValue= "0") boolean c) {
		notes.setC(c);
	}
	
	@RequestMapping("/setD")
	public void setD(@RequestParam(value="d", defaultValue= "0") boolean d) {
		notes.setD(d);
	}
	
	@RequestMapping("/setE")
	public void setE(@RequestParam(value="e", defaultValue= "0") boolean e) {
		notes.setE(e);
	}
	
	@RequestMapping("/setG")
	public void setG(@RequestParam(value="g", defaultValue= "0") boolean g) {
		notes.setG(g);
	}
	
	@RequestMapping("/setA")
	public void setA(@RequestParam(value="a", defaultValue= "0") boolean a) {
		notes.setA(a);
	}
	
	
}
