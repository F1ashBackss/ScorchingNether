# ScorchingNether
Небольшой плагин для Paper, позволяющий администратору включить настраиваемый урон от огня для игроков в Нижнем мире
# Использование
- /sn enable - включить плагин (по умолчанию включен)
- /sn disable - выключить плагин
- /sn reload - перезагрузить плагин
# Конфигурация
```yml
plugin-enabled: true
visual:
  visual-fire-enabled: true
  action-bar-enabled: true
  action-bar-text: §cВ незере ещё слишком горячо
damage:
  damage-enabled: true
  damage-interval: 60
  damage-value: 2
  ```
# Права
- scorchingnether.admin - использование команды /sn
