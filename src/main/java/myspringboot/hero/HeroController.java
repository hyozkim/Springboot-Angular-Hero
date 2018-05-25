package myspringboot.hero;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/* 
 * RESTful 하다!
 */


// CrossOrigin을 주석해야 Postman 테스트 가능
// @CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
@RequestMapping(value = "/heroes")
public class HeroController {

	private List<Hero> heroes = new ArrayList<>();	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	HeroController() {
		buildheroes();
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Hero> getheroes() {
		logger.debug("목록조회");
		return this.heroes;
	}

	// heroes/11
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Hero getHero(@PathVariable("id") Long id) {
		logger.debug("상세조회 " + id);
		// JAVA 8 문법 -> 람다식
		// stream() 이후->  Hero 객체
		// filter 조건
		// findFirst() 찾는다.
		// orElse 없다면 null
		return this.heroes.stream().filter(Hero -> Hero.getId() == id).findFirst().orElse(null);
	}

	/// 등록 요청
	// Parameter Hero로 올때 id: null, name: [값]
	@RequestMapping(method = RequestMethod.POST)
	public Hero saveHero(@RequestBody Hero hero) {
		Long nextId = 0L;
		if (this.heroes.size() != 0) {
			// stream() 이후 -> Hero 객체
			// skip(size) size 전까지 skip! 
			// findFirst 그것을 가져와라
			// orElse 없다면 null
			Hero lastHero = this.heroes.stream().skip(this.heroes.size() - 1).findFirst().orElse(null);
			nextId = lastHero.getId() + 1;
		}

		hero.setId(nextId);
		this.heroes.add(hero);
		return hero;

	}

	// 수정 요청
	@RequestMapping(method = RequestMethod.PUT)
	public Hero updateHero(@RequestBody Hero hero) {
		Hero modifiedHero = this.heroes.stream().filter(u -> u.getId() == hero.getId()).findFirst().orElse(null);
		modifiedHero.setName(hero.getName());
		return modifiedHero;
	}
	
	// 삭제 요청
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean deleteHero(@PathVariable Long id) {
		Hero deleteHero = this.heroes.stream().filter(Hero -> Hero.getId() == id).findFirst().orElse(null);
		if (deleteHero != null) {
			this.heroes.remove(deleteHero);
			return true;
		} else  {
			return false;
		}
	}
	
	// 검색 요청
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	public List<Hero> searchHeroes(@PathVariable String name) {
		return this.heroes.stream().filter(Hero -> Hero.getName().contains(name)).collect(Collectors.toList());
	}

	void buildheroes() {
		Hero hero1 = new Hero(11L,"Miss. Nice");
		Hero hero2 = new Hero(12L,"Marco");
		Hero hero3 = new Hero(13L,"Bombastic");
		Hero hero4 = new Hero(14L,"Celena");
		Hero hero5 = new Hero(15L,"Magneta");
		Hero hero6 = new Hero(16L,"RubberMan");
		Hero hero7 = new Hero(16L,"Dynama");
		Hero hero8 = new Hero(18L,"Dr IQ");
		Hero hero9 = new Hero(19L,"Magma");
		Hero hero10 = new Hero(20L,"Tornado");

		heroes.add(hero1);
		heroes.add(hero2);
		heroes.add(hero3);
		heroes.add(hero4);
		heroes.add(hero5);
		heroes.add(hero6);
		heroes.add(hero7);
		heroes.add(hero8);
		heroes.add(hero9);
		heroes.add(hero10);
	}
}