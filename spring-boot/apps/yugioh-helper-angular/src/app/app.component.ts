import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {YugiohControllerApi, YugiohDeckControllerApi} from '../api';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'yugioh-proxy-builder-angular';
  static API = new YugiohControllerApi();
  static DECK_API = new YugiohDeckControllerApi();
}
