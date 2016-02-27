'use strict'

const INFO = [
  {id: 1, lineName: 'olympicPark', "Content": [
    {time: 'Saturday 13 February 02:00 to Monday 15 February 02:00', content: 'Buses replace trains between Berowra and Hornsby.'}
  ]},
  {id: 11, lineName: 'hunter', "Content": [
    {time: 'Saturday 13 February 02:00 to Monday 15 February 02:00', content: 'Buses replace trains between Scone, Dungog and Hamilton then continue to Newcastle.'},
    {time: 'Monday 15 February 23:20 to Tuesday 16 February 01:30', content: 'The 23:21 and the 00:21 Hamilton to Telarah services leave Hamilton 20 minutes later than the normal timetable.'},
    {time: 'Tuesday 16 February 23:20 to Wednesday 17 February 01:30', content: 'The 23:21 and the 00:21 Hamilton to Telarah services leave Hamilton 20 minutes later than the normal timetable.'}
  ]},
  {id: 14, lineName: 'southCoast', "Content": [
    {time: 'Saturday 13 February 02:00 to Monday 15 February 02:00', content: 'Buses replace trains between Scone, Dungog and Hamilton then continue to Newcastle.'},
    {time: 'Monday 15 February 23:20 to Tuesday 16 February 01:30', content: 'The 23:21 and the 00:21 Hamilton to Telarah services leave Hamilton 20 minutes later than the normal timetable.'},
    {time: 'Tuesday 16 February 23:20 to Wednesday 17 February 01:30', content: 'The 23:21 and the 00:21 Hamilton to Telarah services leave Hamilton 20 minutes later than the normal timetable.'}
  ]},
  {id: 15, lineName: 'newcastle', "Content": [
    {time: 'Saturday 13 February 02:00 to Monday 15 February 02:00', content: 'Buses replace trains between Scone, Dungog and Hamilton then continue to Newcastle.'},
    {time: 'Monday 15 February 23:20 to Tuesday 16 February 01:30', content: 'The 23:21 and the 00:21 Hamilton to Telarah services leave Hamilton 20 minutes later than the normal timetable.'},
    {time: 'Tuesday 16 February 23:20 to Wednesday 17 February 01:30', content: 'The 23:21 and the 00:21 Hamilton to Telarah services leave Hamilton 20 minutes later than the normal timetable.'}
  ]},
  {id: 16, lineName: 'blueMountains', "Content": [
    {time: 'Saturday 13 February 02:00 to Monday 15 February 02:00', content: 'Buses replace trains between Scone, Dungog and Hamilton then continue to Newcastle.'},
    {time: 'Monday 15 February 23:20 to Tuesday 16 February 01:30', content: 'The 23:21 and the 00:21 Hamilton to Telarah services leave Hamilton 20 minutes later than the normal timetable.'},
    {time: 'Tuesday 16 February 23:20 to Wednesday 17 February 01:30', content: 'The 23:21 and the 00:21 Hamilton to Telarah services leave Hamilton 20 minutes later than the normal timetable.'}
  ]}
];

export function inCommingNewsParser(data) {
  return INFO;
}