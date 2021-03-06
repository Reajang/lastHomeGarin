#language: ru

Функционал: Итоговый тест Ozon

  @all @first
  Сценарий: Первый
    * Перейти на сайт "http://www.ozon.ru/"
    * Выполнить авторизацию "leon.viktor@mail.ru" "D4ZDcOm9"
#      | login    |  | leon.viktor@mail.ru |
#      | password |  | "D4ZDcOm9"            |
    * Выполнить поиск по "iphone"
    * Ограничить цену сверху до 60000
    * Добавить 8 товаров в корзину. Условие - "нечетные"
    * Проверить что все товары добавлены в корзину
    * Проверить, что итоговая цена равна сумме цен добавленных товаров
    * Удалить все товары из корзины
    * Разлогиниться
    * Выполнить авторизацию "leon.viktor@mail.ru" "D4ZDcOm9"
    * Проверить что корзина пуста

  @all @second
  Сценарий: Второй
    * Перейти на сайт "http://www.ozon.ru/"
    * Выполнить авторизацию "leon.viktor@mail.ru" "D4ZDcOm9"
    * Выполнить поиск по "беспроводные наушники"
    * Ограничить цену сверху до 10000
    * Выбрать бренды
      | Beats   |
      | Samsung |
    * Добавить 4 товаров в корзину. Условие - "четные"
    * Проверить что все товары добавлены в корзину
    * Проверить, что итоговая цена равна сумме цен добавленных товаров
    * Удалить все товары из корзины
    * Разлогиниться
    * Выполнить авторизацию "leon.viktor@mail.ru" "D4ZDcOm9"
    * Проверить что корзина пуста

