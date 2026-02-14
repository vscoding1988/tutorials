import {Component, Input} from '@angular/core';
import {CardDTO} from '../../../api';

@Component({
  selector: 'app-deck-card-overview',
  imports: [],
  templateUrl: './deck-card-overview.component.html',
  styleUrl: './deck-card-overview.component.scss'
})
export class DeckCardOverviewComponent {
  @Input() title: string = "";
  @Input() cards: Array<CardDTO> = [];
  @Input() listView: boolean = true;

  groupCards(): Array<any> {
    if (!this.cards) {
      return [];
    }
    let grouping: any = {};

    this.cards.forEach(card => {
      if (card.name && !grouping[card.name]) {
        grouping[card.name] = {
          "card": card,
          "count": 1
        }
      } else if (card.name) {
        let stats = grouping[card.name];
        stats.count += 1;
      }
    });

    return Object.values(grouping);
  }
}
