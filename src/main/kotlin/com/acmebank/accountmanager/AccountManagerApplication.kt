package com.acmebank.accountmanager

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.acmebank.accountmanager.springframework.context")
class AccountManagerApplication {

	companion object {

		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(AccountManagerApplication::class.java, *args)
		}

	}

}


