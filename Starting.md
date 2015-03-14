# Необходимые (желательные) инструменты #

  * Android SDK - http://developer.android.com/sdk/index.html
  * Eclipse (Java) - http://www.eclipse.org/downloads/
  * Плагин на Eclipse (Android) - http://developer.android.com/sdk/installing.html
  * Плагин на Eclipse (работа с SVN) - http://subclipse.tigris.org/

# Процесс настройки #

Установка вышеуказанных компонентов не представляет сложности.

Для того, чтобы добавить проект в свой workbench на Eclips'е, делаем следующее:

  1. File -> Import...
  1. SVN -> Checkout Projects from SVN -> Next
  1. Create a new repository location -> Next
  1. В поле "Url" вписываем: https://hpc-android.googlecode.com/svn/trunk/
  1. **В появившемся окне выбираем подпапку hpc-android** -> Next
  1. Если всё сделано правильно, в новом окне будет доступен только пункт "Check out as a project in the workspace"
  1. Все прочие настройки можно оставить без изменений, жмём Finish

_Если вдруг попросит ввести пароль, нужно взять его со страницы http://code.google.com/p/hpc-android/source/checkout -> googlecode.com password._

После этих манипуляций в вашем списке появится проект hpc-android, доступный для редактирования.

Функционал Subversion доступен во вкладке Team (правый щелчок по проекту -> Team). Основные рабочие поля - Commit, Sinchronize.