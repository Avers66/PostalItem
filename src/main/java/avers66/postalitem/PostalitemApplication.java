package avers66.postalitem;

import avers66.postalitem.data.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.ZonedDateTime;

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

				PostOffice krasnodar = new PostOffice(null, "Krasnodar", "350000", "Краснодар,ул Карасунская, дом 68");
				PostOffice moscow = new PostOffice(null, "Moscow", "101000", "Москва,ул. Мясницкая, д. 26");
				PostOffice novosibirsk = new PostOffice(null, "Novosibirsk", "630000", "Новосибирск,ул Дмитрия Шамшурина, дом 45");
				postRepo.save(krasnodar);
				postRepo.save(moscow);
				postRepo.save(novosibirsk);

				PostalDelivery letter1 = new PostalDelivery(null, PostalDelivery.Type.LETTER, "630000", "Новосибирск, ул Северная, 1", "Александр Т.", Status.RECEIVING, ZonedDateTime.now(), novosibirsk);
				PostalDelivery package1 = new PostalDelivery(null, PostalDelivery.Type.PACKAGE, "350000", "Краснодар, ул Южная, 2", "Александр Т.", Status.RECEIVING, ZonedDateTime.now(), krasnodar);
				postalRepo.save(letter1);
				postalRepo.save(package1);

				statusRepo.save(new StatusHistory(null, letter1, Status.REGISTRATION, ZonedDateTime.now().minusDays(5), krasnodar));
				statusRepo.save(new StatusHistory(null, letter1, Status.DEPARTURE, ZonedDateTime.now().minusDays(4), krasnodar));
				statusRepo.save(new StatusHistory(null, letter1, Status.ARRIVAL, ZonedDateTime.now().minusDays(3), moscow));
				statusRepo.save(new StatusHistory(null, letter1, Status.DEPARTURE, ZonedDateTime.now().minusDays(2), moscow));
				statusRepo.save(new StatusHistory(null, letter1, Status.ARRIVAL, ZonedDateTime.now().minusDays(1), novosibirsk));
				statusRepo.save(new StatusHistory(null, letter1, Status.RECEIVING, ZonedDateTime.now(), novosibirsk));

				statusRepo.save(new StatusHistory(null, package1, Status.REGISTRATION, ZonedDateTime.now().minusDays(5), novosibirsk));
				statusRepo.save(new StatusHistory(null, package1, Status.DEPARTURE, ZonedDateTime.now().minusDays(4), novosibirsk));
				statusRepo.save(new StatusHistory(null, package1, Status.ARRIVAL, ZonedDateTime.now().minusDays(3), moscow));
				statusRepo.save(new StatusHistory(null, package1, Status.DEPARTURE, ZonedDateTime.now().minusDays(2), moscow));
				statusRepo.save(new StatusHistory(null, package1, Status.ARRIVAL, ZonedDateTime.now().minusDays(1), krasnodar));
				statusRepo.save(new StatusHistory(null, package1, Status.RECEIVING, ZonedDateTime.now(), krasnodar));



			}
		};
	}
}
