import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ImageToPdfComponent } from './image-to-pdf/image-to-pdf.component';

@Component({
  imports: [RouterOutlet, ImageToPdfComponent],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('image-to-pdf-ui');
}
