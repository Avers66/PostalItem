package avers66.postalitem;

import avers66.postalitem.data.PostOffice;
import avers66.postalitem.data.PostRepository;
import avers66.postalitem.data.PostalRepository;
import avers66.postalitem.data.StatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PostalitemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostalitemApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(PostRepository postRepo,
										PostalRepository postalRepo,
										StatusRepository statusRepo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				postRepo.save(new PostOffice(null, "Krasnodar", "350000", "Краснодар,ул Карасунская, дом 68"));
				postRepo.save(new PostOffice(null, "Moscow", "101000", "Москва,ул. Мясницкая, д. 26"));
				postRepo.save(new PostOffice(null, "Novosibirsk", "630000", "Новосибирск,ул Дмитрия Шамшурина, дом 45"));
			}
		};
	}
}
