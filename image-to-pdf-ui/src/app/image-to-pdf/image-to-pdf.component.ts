import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-image-to-pdf',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './image-to-pdf.component.html',
  styleUrls: ['./image-to-pdf.component.css']
})
export class ImageToPdfComponent {

  folderPath = '';
  loading = false;
  message = '';
  error = '';

  private readonly apiUrl =
    'http://localhost:8080/api/pdf/generate';

  constructor(private http: HttpClient) {}

  generatePdf(): void {

    if (!this.folderPath.trim()) {
      this.error = 'Please enter a folder path.';
      this.message = '';
      return;
    }

    this.loading = true;
    this.message = '';
    this.error = '';

    this.http.post(
      this.apiUrl,
      null,
      {
        params: {
          folderPath: this.folderPath.trim()
        },
        responseType: 'blob'
      }
    ).subscribe({

      next: (blob) => {

        this.loading = false;

        const url =
          URL.createObjectURL(blob);

        const link =
          document.createElement('a');

        link.href = url;
        link.download = 'images.pdf';
        link.click();

        URL.revokeObjectURL(url);

        this.message =
          'PDF generated successfully.';
      },

      error: (error) => {

        this.loading = false;

        console.error(error);

        this.error =
          'Failed to generate PDF.';
      }
    });
  }
}