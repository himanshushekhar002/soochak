package com.notificationlab.soochak.template.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.notificationlab.soochak.constants.FileType;
import com.notificationlab.soochak.constants.MessageType;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TemplateRepository {
	
	@Value("${soochak.template.folder}")
	private String templateFolderPath;
	
	public String getEmailTemplatePath() {
		return templateFolderPath+"email/";
	}
	
	public String getSmsTemplatePath() {
		return templateFolderPath+"sms/";
	}
	
	public String getPnTemplatePath() {
		return templateFolderPath+"pn/";
	}

	public void save(final MessageType templateCategory, final String templateName, final String templateContent,
			final FileType templateExtension) throws IOException {
		switch (templateCategory) {
		case EMAIL:
			saveEmail(templateName, templateContent, templateExtension);
			break;
		case SMS:
			saveSMS(templateName, templateContent, templateExtension);
			break;
		case PN:
			savePN(templateName, templateContent, templateExtension);
			break;
		}
	}

	public String get(final MessageType templateCategory, final String templateName, final String templateContent,
			final FileType templateExtension) throws IOException {
		switch (templateCategory) {
		case EMAIL:
			return getEmail(templateName, templateExtension);
		case SMS:
			return getSMS(templateName, templateExtension);
		case PN:
			return getPN(templateName, templateExtension);
		default:
			return null;
		}
	}

	private void saveEmail(final String templateName, final String templateContent, final FileType templateExtension)
			throws IOException {
		File folder = new File(getEmailTemplatePath());
		if(!folder.exists()) {
			folder.mkdir();
		}
		File file = createTemplateIfNotExist(folder, templateName, templateExtension);
		writeToFile(file, templateContent);
	}

	private void saveSMS(final String templateName, final String templateContent, final FileType templateExtension)
			throws IOException {
		File folder = new File(getSmsTemplatePath());
		if(!folder.exists()) {
			folder.mkdir();
		}
		File file = createTemplateIfNotExist(folder, templateName, templateExtension);
		writeToFile(file, templateContent);
	}

	private void savePN(final String templateName, final String templateContent, final FileType templateExtension)
			throws IOException {
		File folder = new File(getPnTemplatePath());
		if(!folder.exists()) {
			folder.mkdir();
		}
		File file = createTemplateIfNotExist(folder, templateName, templateExtension);
		writeToFile(file, templateContent);
	}

	private File createTemplateIfNotExist(final File pathToFolder, final String templateName,
			final FileType templateExtension) throws IOException {
		log.info("ABSOLUTE PATH TO FOLDER :: " + pathToFolder.getAbsolutePath());
		File file = new File(
				pathToFolder.getAbsolutePath() + File.separator + templateName + templateExtension.getValue());
		if (!file.exists() && file.createNewFile()) {
			log.info("File is created! :: " + file.getAbsolutePath());
		} else {
			log.info("File already exists :: " + file.getAbsolutePath());
		}
		return file;
	}

	private void writeToFile(File completeFilePath, String content) throws IOException {
		FileLock lock = null;
		boolean fileLockAcquired = false;
		try (RandomAccessFile stream = new RandomAccessFile(completeFilePath, "rws");
				FileChannel channel = stream.getChannel();) {
			lock = channel.tryLock();
			if (lock != null) {
				fileLockAcquired = true;
			}
			/*
			 * TODO
			 * //Will delete file content 
			 * In  future you will have to handle scenarios
			 * in which this operation is going on and 
			 * template processing is also happening, then
			 * it may happen that template processor
			 * may see a blank file or partially written file.
			 * So basically template processor must wait until this file
			 * write is finished. 
			 *  
			 */
			stream.setLength(0);
			stream.writeBytes(content);
			if (fileLockAcquired) {
				lock.release();
			}
		}
	}

	private String getEmail(final String templateName, final FileType templateExtension) throws IOException {
		File file = new File(templateFolderPath+"email/" + templateName
				+ templateExtension.getValue());
		return getContentFromFile(file);
	}

	private String getSMS(final String templateName, final FileType templateExtension) throws IOException {
		File file = new File(templateFolderPath+"sms/" + templateName
				+ templateExtension.getValue());
		return getContentFromFile(file);
	}

	private String getPN(final String templateName, final FileType templateExtension) throws IOException {
		File file = new File(templateFolderPath+"pn/" + templateName
				+ templateExtension.getValue());
		return getContentFromFile(file);
	}

	private String getContentFromFile(File completeFilePath) throws IOException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(completeFilePath), StandardCharsets.UTF_8));) {
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		}
	}
}
