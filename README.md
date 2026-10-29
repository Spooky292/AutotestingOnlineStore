# 🛒 Автоматизированное тестирование E-Commerce платформы | E-Commerce QA Automation

🌐 **Language / Язык:** [🇷🇺 Русский](#-описание-проекта-ru) | [🇬🇧 English](#-project-overview-en)

---

## 🇷🇺 Описание проекта (RU)

Комплексный набор автотестов (UI, Smoke, Regression) для проверки критически важных бизнес-сценариев интернет-магазина: от регистрации пользователя до успешного оформления заказа в корзине.

**Бизнес-задача:** Продемонстрировать чистую архитектуру автотестов, стабильность локаторов и подход к защите интернет-магазинов от потери прибыли из-за технических багов после обновлений.

### 🛠 Стек технологий
* **Язык программирования:** Java 17 (JDK 17)
* **Тест-фреймворк:** JUnit 4
* **Сборщик проекта:** Maven (`pom.xml`)
* **Автоматизация браузера:** Selenium WebDriver, ChromeDriver
* **Инструменты отладки:** Chrome DevTools, IntelliJ IDEA, Jira, Git

### 📂 Архитектура тестов (`src/test/java`)
1. **`RegistrationTests.java`** — проверка создания аккаунта и валидация полей.
2. **`AuthorizationTests.java`** — тестирование входа в систему и обработки ошибок доступа.
3. **`CatalogTests.java` & `MainTests.java`** — проверка витрины, поиска, фильтрации и карточек товаров.
4. **`BasketTests.java`** — тестирование добавления/удаления товаров, пересчета сумм и промокодов.
5. **`MakingAnOrderTests.java`** — сквозная проверка оформления заказа и перехода к оплате.

### ⚙️ Тестовый стенд (Sandbox) и запуск
В соответствии с правилами безопасности и профессиональной этикой QA, публичные демонстрационные сценарии не запускаются на рабочих серверах клиентов во избежание паразитной нагрузки. Проект настроен для работы на открытом тестовом полигоне:
* **BASE_URL:** `https://skillbox.ru` (Публичный демо-стенд)
* **Запуск из консоли:** `mvn test -Dbase.url=https://skillbox.ru`

---

## 🇬🇧 Project Overview (EN)

A comprehensive suite of automated UI, Smoke, and Regression tests designed to verify mission-critical user journeys in an E-Commerce web application — from user registration to checkout and payment processing.

**Business Goal:** Showcase scalable test automation architecture, robust element locators, and QA methodology for preventing revenue loss caused by post-release regression bugs.

### 🛠 Tech Stack & Tools
* **Language:** Java 17 (JDK 17)
* **Testing Framework:** JUnit 4
* **Build Tool:** Maven (`pom.xml`)
* **Web Automation:** Selenium WebDriver, ChromeDriver
* **Debugging & Tracking:** Chrome DevTools, IntelliJ IDEA, Jira, Git

### 📂 Test Suite Structure (`src/test/java`)
1. **`RegistrationTests.java`** — user sign-up flow and input field validation.
2. **`AuthorizationTests.java`** — login functionality and invalid credential handling.
3. **`CatalogTests.java` & `MainTests.java`** — product catalog rendering, search bar, filtering, and item cards.
4. **`BasketTests.java`** — shopping cart operations, price calculation, and promo code application.
5. **`MakingAnOrderTests.java`** — end-to-end (E2E) checkout process and payment gateway redirection.

### ⚙️ Sandbox Environment & Execution
In accordance with QA ethics and security best practices, public demo scripts are not executed against live client production servers to avoid unnecessary traffic load. To ensure 100% test reproducibility, this suite runs against a dedicated public E-Commerce sandbox environment:
* **BASE_URL:** `https://skillbox.ru` (Public QA Sandbox)
* **Run via CLI:** `mvn test -Dbase.url=https://skillbox.ru`

---
📧 **Contact for QA & Development inquiries:** Telegram `@Davidx5`
